package com.wishes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.repository.SaldoRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.SaldoEntity
import com.wishes.database.entity.WishEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
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
        initialValue = 0.toBigDecimal()
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

    fun subtrairSaldo(saldo: BigDecimal){
        viewModelScope.launch {
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

    private val pagConfig = PagingConfig(20)

    val pagingWishes = buscarWishes.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            buscarWishes(BuscarWishes.Params(pagConfig))
        }
    }
}
