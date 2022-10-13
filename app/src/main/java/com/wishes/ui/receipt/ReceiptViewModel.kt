package com.wishes.ui.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.domain.BuscarWishesComprados
import com.wishes.data.domain.BuscarWishesCompradosUltimoMes
import com.wishes.data.repository.SaldoRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.SaldoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val saldoRepository: SaldoRepository,
    private val buscarWishesComprados: BuscarWishesComprados,
    private val buscarWishesCompradosUltimoMes: BuscarWishesCompradosUltimoMes,
) : ViewModel() {
    private val pagConfig = PagingConfig(20)

    val pagingItems = buscarWishesComprados.flow.cachedIn(viewModelScope)
    val pagingItemsUltimoMes = buscarWishesComprados.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            buscarWishesComprados(BuscarWishesComprados.Params(pagConfig))
            buscarWishesCompradosUltimoMes(BuscarWishesCompradosUltimoMes.Params(pagConfig))
        }
    }
}
