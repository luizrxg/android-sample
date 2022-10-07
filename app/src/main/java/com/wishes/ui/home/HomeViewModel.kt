package com.wishes.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.repository.WishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val buscarWishes: BuscarWishes
) : ViewModel() {

    private val pagConfig = PagingConfig(20)

    val pagingWishes = buscarWishes.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            buscarWishes(BuscarWishes.Params(pagConfig))
        }
    }
}
