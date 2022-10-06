package com.defaultapp.data.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.defaultapp.data.model.Default
import com.defaultapp.data.repository.DefaultRepository
import com.defaultapp.database.entity.DefaultEntity
import com.defaultapp.database.entity.asExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//class BuscarDefault @Inject constructor(
//    private val defaultRepository: DefaultRepository
//) : PagingInteractor<BuscarDefault.Params, Default>() {
//
//    data class Params(override val pagingConfig: PagingConfig) :
//        Parameters<Default>
//
//    override suspend fun createObservable(
//        params: Params
//    ): Flow<PagingData<Default>> = Pager(config = params.pagingConfig) {
//        defaultRepository.buscarDefault()
//    }.flow.map { it.map(DefaultEntity::asExternalModel) }
//
//}