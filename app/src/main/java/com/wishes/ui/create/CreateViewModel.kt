package com.wishes.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wishes.data.domain.BuscarWishes
import com.wishes.data.repository.LinkRepository
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.LinkEntity
import com.wishes.database.entity.WishEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel  @Inject internal constructor(
    private val wishRepository: WishRepository,
    private val linkRepository: LinkRepository,
) : ViewModel() {

    private val id = wishRepository.buscarUltimoId()

    val stateId: StateFlow<Long> = id.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun criarWish(wish: WishEntity){
        viewModelScope.launch {
            wishRepository.criarWish(wish)
        }
    }

    fun criarLinks(
        links: List<String?>,
        id: Long
    ){
        links.forEach {
            it?.let {
                viewModelScope.launch {
                    linkRepository.criarLink(
                        LinkEntity(0, id, it)
                    )
                }
            }
        }
    }
}
