package com.wishes.data.repository

import androidx.paging.PagingSource
import com.wishes.data.model.Wish
import com.wishes.database.dao.WishDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.WishEntity
import com.wishes.util.Month
import com.wishes.util.Week
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishRepository @Inject constructor(
    val wishDao: WishDao,
    val transactionRunner: DatabaseTransactionRunner
) {
    private val monthStart = "${Month.MONTH_START.monthDay}"
    private val monthEnd = "${Month.MONTH_END.monthDay}"

    fun buscarWishPorId(id: Long): Flow<Wish?>{
        return wishDao.buscarWishPorId(id)
    }

    fun buscarWishes(): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishes()
    }

    fun buscarWishes(search: String): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishes(search)
    }

    fun buscarWishesUltimoMes(): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishesUltimoMes(monthStart, monthEnd)
    }

    fun buscarWishesComprados(): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishesComprados()
    }

    fun buscarWishesCompradosUltimoMes(): PagingSource<Int, WishEntity> {
        return wishDao.buscarWishesCompradosUltimoMes(monthStart, monthEnd)
    }

    fun buscarUltimoId(): Flow<Long?> {
        return wishDao.buscarUltimoId()
    }

    suspend fun criarWish(wish: WishEntity) {
        wishDao.criarWish(wish)
    }

    suspend fun comprarWish(id: Long){
        wishDao.comprarWish(id)
    }

    suspend fun delete(wish: WishEntity){
        wishDao.delete(wish)
    }

    suspend fun deleteById(id: Long){
        wishDao.deleteById(id)
    }
}