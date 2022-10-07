package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wishes.database.entity.WishEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao : EntityDao<WishEntity>() {

    @Query("SELECT * from wishes")
    abstract fun buscarWishes(): PagingSource<Int, WishEntity>

    @Query("SELECT max(id) from wishes")
    abstract fun buscarUltimoId(): Flow<Long>

    @Insert
    abstract suspend fun criarWish(wish: WishEntity)
}

