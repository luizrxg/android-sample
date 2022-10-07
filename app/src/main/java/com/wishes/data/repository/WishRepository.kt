package com.wishes.data.repository

import androidx.paging.PagingSource
import com.wishes.database.dao.WishDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.WishEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishRepository @Inject constructor(
    val wishDao: WishDao,
    val transactionRunner: DatabaseTransactionRunner
) {
    fun buscarWishes(): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishes()
    }

    fun buscarUltimoId(): Flow<Long> {
        return wishDao.buscarUltimoId()
    }

    suspend fun criarWish(wish: WishEntity) {
        wishDao.criarWish(wish)
    }
}