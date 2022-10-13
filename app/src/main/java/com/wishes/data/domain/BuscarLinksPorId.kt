package com.wishes.data.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.wishes.data.model.Link
import com.wishes.data.repository.LinkRepository
import com.wishes.database.entity.LinkEntity
import com.wishes.database.entity.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BuscarLinksPorId @Inject constructor(
    private val linkRepository: LinkRepository
) : PagingInteractor<BuscarLinksPorId.Params, Link>() {

    data class Params(override val pagingConfig: PagingConfig, val id: Long) :
        Parameters<Link>

    override suspend fun createObservable(
        params: Params
    ): Flow<PagingData<Link>> = Pager(config = params.pagingConfig) {
        linkRepository.buscarLinksPorId(params.id)
    }.flow.map { it.map(LinkEntity::asExternalModel) }

}