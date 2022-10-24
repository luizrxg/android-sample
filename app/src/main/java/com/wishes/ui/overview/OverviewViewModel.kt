package com.wishes.ui.overview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarLinksPorId
import com.wishes.data.model.Wish
import com.wishes.data.repository.LinkRepository
import com.wishes.data.repository.SaldoRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.LinkEntity
import com.wishes.ui.commons.UiMessage
import com.wishes.ui.commons.UiMessageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val linkRepository: LinkRepository,
    private val saldoRepository: SaldoRepository,
    private val buscarLinksPorId: BuscarLinksPorId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val pagConfig = PagingConfig(20)

    private val id: Long = checkNotNull(
        savedStateHandle[OverviewDestination.id]
    )

    private val wish = wishRepository.buscarWishPorId(id)

    private val saldo = saldoRepository.buscarSaldo()

    val pagingLinks = buscarLinksPorId.flow.cachedIn(viewModelScope)

    private val temSaldo = MutableStateFlow(false)
    private val uiMessage = UiMessageManager()

    val uiState: StateFlow<UiState> =
        combine(wish, saldo){ wish, saldo ->
            UiState(wish, saldo)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Empty
        )

    fun comprarWish(){
        viewModelScope.launch {
            if (uiState.value.saldo!! > uiState.value.wish!!.preco){
                temSaldo.value = true
                wishRepository.comprarWish(id)
                saldoRepository.atualizarSaldo(uiState.value.saldo!! - uiState.value.wish?.preco!!)
            } else {
                temSaldo.value = false
                uiMessage.emitMessage(UiMessage("Saldo insuficiente !"))
            }
        }
    }

    val stateComprar: StateFlow<ComprarUiState> =
        combine(temSaldo, uiMessage.message){ temSaldo, message ->
            ComprarUiState(temSaldo, message)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ComprarUiState.Empty
        )

    fun clearMessage() {
        viewModelScope.launch {
            uiMessage.clearAll()
        }
    }

    fun deleteWish(){
        viewModelScope.launch {
            wishRepository.deleteById(id)
        }
    }

    fun criarLink(link: LinkEntity){
        viewModelScope.launch {
            linkRepository.criarLink(link)
        }
    }

    fun deleteLink(link: LinkEntity){
        viewModelScope.launch {
            linkRepository.deleteLink(link)
        }
    }

    init {
        viewModelScope.launch {
             buscarLinksPorId(BuscarLinksPorId.Params(pagConfig, id))
        }
    }
}

data class ComprarUiState(
    val temSaldo: Boolean? = false,
    val message: UiMessage? = null
){
    companion object{
        val Empty = ComprarUiState()
    }
}

data class UiState(
    val wish: Wish? = null,
    val saldo: BigDecimal? = BigDecimal.ZERO
){
    companion object{
        val Empty = UiState()
    }
}