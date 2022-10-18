package com.wishes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.model.Wish
import com.wishes.data.repository.SaldoRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.SaldoEntity
import com.wishes.database.entity.WishEntity
import com.wishes.ui.commons.UiMessage
import com.wishes.ui.commons.UiMessageManager
import com.wishes.ui.overview.ComprarUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val saldoRepository: SaldoRepository,
    private val buscarWishes: BuscarWishes
) : ViewModel() {

    private val saldo = saldoRepository.buscarSaldo()

    val stateSaldo: StateFlow<BigDecimal?> = saldo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BigDecimal.ZERO
    )

    fun criarSaldo(saldo: SaldoEntity){
        viewModelScope.launch {
            saldoRepository.criarSaldo(saldo)
        }
    }

    fun adicionarSaldo(saldo: BigDecimal){
        viewModelScope.launch {
            saldoRepository.atualizarSaldo(stateSaldo.value!! + saldo)
            wishRepository.criarWish(
                WishEntity(
                    id = 0,
                    nome = "Saldo adicionado",
                    preco = saldo,
                    prioridade = 3,
                    comprado = true,
                    data = "${LocalDateTime.now()}"
                )
            )
        }
    }

    private val temSaldo = MutableStateFlow(false)
    private val uiMessage = UiMessageManager()

    fun subtrairSaldo(saldo: BigDecimal){
        val value = stateSaldo.value!! - saldo

        viewModelScope.launch {
            if (value < BigDecimal.ZERO){
                temSaldo.value = false
                uiMessage.emitMessage(UiMessage("Saldo insuficiente !"))
            } else {
                temSaldo.value = true
                saldoRepository.atualizarSaldo(stateSaldo.value!! - saldo)
                wishRepository.criarWish(
                    WishEntity(
                        id = 0,
                        nome = "Saldo subtraído",
                        preco = saldo,
                        prioridade = 4,
                        comprado = true,
                        data = "${LocalDateTime.now()}"
                    )
                )
            }
        }
    }

    val uiState: StateFlow<UiState> =
        combine(temSaldo, uiMessage.message){ temSaldo, message ->
            UiState(temSaldo, message)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Empty
        )

    fun clearMessage() {
        viewModelScope.launch {
            uiMessage.clearAll()
        }
    }

    private val pagConfig = PagingConfig(20)

    val pagingWishes = buscarWishes.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            buscarWishes(BuscarWishes.Params(pagConfig))
        }
    }
}

data class UiState(
    val temSaldo: Boolean? = false,
    val message: UiMessage? = null
){
    companion object{
        val Empty = UiState()
    }
}