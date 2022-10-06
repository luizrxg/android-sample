package com.defaultapp.data.repository

import androidx.paging.PagingSource
import com.defaultapp.data.model.Default
import com.defaultapp.database.dao.DefaultDao
import com.defaultapp.database.db.DatabaseTransactionRunner
import com.defaultapp.database.entity.DefaultEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRepository @Inject constructor(
    val defaultDao: DefaultDao,
    val transactionRunner: DatabaseTransactionRunner
) {
//    fun buscarDefault(): PagingSource<Int, DefaultEntity> {
//        return defaultDao.buscarDefault()
//    }
}