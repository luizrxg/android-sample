package com.wishes.ui.simulation

import androidx.compose.runtime.MutableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.model.Wish
import com.wishes.data.repository.SaldoRepository
import com.wishes.data.repository.WishRepository
import com.wishes.util.formatToFixed2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class SimulationViewModel @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val saldoRepository: SaldoRepository,
    private val buscarWishes: BuscarWishes,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val saldo = saldoRepository.buscarSaldo()

    val stateSaldo: StateFlow<BigDecimal?> = saldo.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BigDecimal.ZERO
    )

    private val selectedWishes = MutableStateFlow<List<Wish>>(emptyList())

    val stateSelectedWishes: StateFlow<List<Wish>> = selectedWishes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectWish(wish: Wish) { selectedWishes.value += wish }

    fun unselectWish(wish: Wish) { selectedWishes.value -= wish }

    private val total = MutableStateFlow(BigDecimal.ZERO)
    private val resto = MutableStateFlow(BigDecimal.ZERO)

    val uiState: StateFlow<UiState> =
        combine(total, resto){ total, resto ->
            UiState(total, resto)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState.Empty
        )



    private val pagConfig = PagingConfig(20)

    val pagingWishes = buscarWishes.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            selectedWishes
                .debounce(300)
                .catch{ }
                .onEach {
                    buscarWishes(BuscarWishes.Params(pagConfig))
                    total.value = BigDecimal.ZERO
                    selectedWishes.value.map {
                        total.value += it.preco
                    }
                    resto.value = stateSaldo.value?.minus(total.value) ?: BigDecimal.ZERO
                }
                .collect()
        }
    }
}

data class UiState(
    val total: BigDecimal? = null,
    val resto: BigDecimal? = null,
) {
    companion object {
        val Empty = UiState()
    }
}
