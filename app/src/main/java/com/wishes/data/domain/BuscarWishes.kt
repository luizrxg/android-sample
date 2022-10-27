package com.wishes.data.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.wishes.data.model.Wish
import com.wishes.data.repository.WishRepository
import com.wishes.database.entity.WishEntity
import com.wishes.database.entity.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BuscarWishes @Inject constructor(
    private val wishRepository: WishRepository
) : PagingInteractor<BuscarWishes.Params, Wish>() {

    data class Params(override val pagingConfig: PagingConfig, val search: String = "") :
        Parameters<Wish>

    override suspend fun createObservable(
        params: Params
    ): Flow<PagingData<Wish>> = Pager(config = params.pagingConfig) {
        wishRepository.buscarWishes(if(params.search.isEmpty())"%" else "%${params.search}%")
    }.flow.map { it.map(WishEntity::asExternalModel) }

}