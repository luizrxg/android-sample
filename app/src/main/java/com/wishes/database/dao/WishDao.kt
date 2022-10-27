package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wishes.data.model.Wish
import com.wishes.database.entity.WishEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao : EntityDao<WishEntity>() {

    @Query("SELECT * from wishes ORDER BY data DESC")
    abstract fun buscarWishes(): PagingSource<Int, WishEntity>

    @Query("SELECT * from wishes WHERE nome LIKE :search ORDER BY data DESC")
    abstract fun buscarWishes(search: String): PagingSource<Int, WishEntity>

    @Query("SELECT * from wishes WHERE data BETWEEN :primeiroDia AND :ultimoDia ORDER BY data DESC")
    abstract fun buscarWishesUltimoMes(primeiroDia: String, ultimoDia: String): PagingSource<Int, WishEntity>

    @Query("SELECT * from wishes WHERE comprado = 1 ORDER BY data DESC")
    abstract fun buscarWishesComprados(): PagingSource<Int, WishEntity>

    @Query("SELECT * from wishes WHERE comprado = 1 AND data BETWEEN :primeiroDia AND :ultimoDia ORDER BY data DESC")
    abstract fun buscarWishesCompradosUltimoMes(primeiroDia: String, ultimoDia: String): PagingSource<Int, WishEntity>

    @Query("SELECT * from wishes where id = :id")
    abstract fun buscarWishPorId(id: Long): Flow<Wish?>

    @Query("SELECT id from wishes ORDER BY data DESC LIMIT 1")
    abstract fun buscarUltimoId(): Flow<Long?>

    @Query("UPDATE wishes SET comprado = 1 WHERE id = :id ")
    abstract suspend fun comprarWish(id: Long)

    @Insert
    abstract suspend fun criarWish(wish: WishEntity)

    @Delete
    abstract suspend fun delete(wish: WishEntity)

    @Query("DELETE FROM wishes WHERE id = :id")
    abstract suspend fun deleteById(id: Long)
}

