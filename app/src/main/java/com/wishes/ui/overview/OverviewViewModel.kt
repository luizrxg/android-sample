package com.wishes.ui.overview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarLinksPorId
import com.wishes.data.model.Wish
import com.wishes.data.repository.LinkRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.LinkEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val linkRepository: LinkRepository,
    private val buscarLinksPorId: BuscarLinksPorId,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val pagConfig = PagingConfig(20)

    private val id: Long = checkNotNull(
        savedStateHandle[OverviewDestination.id]
    )

    private val wish = wishRepository.buscarWishPorId(id)

    val stateWish: StateFlow<Wish?> = wish.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val pagingLinks = buscarLinksPorId.flow.cachedIn(viewModelScope)

    fun comprarWish(){
        viewModelScope.launch {
            wishRepository.comprarWish(id)
        }
    }

    fun deleteWish(){
        viewModelScope.launch {
            wishRepository.comprarWish(id)
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
