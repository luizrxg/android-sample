package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wishes.database.entity.LinkEntity

@Dao
abstract class LinkDao : EntityDao<LinkEntity>() {

    @Query("SELECT * from links")
    abstract fun buscarLinks(): PagingSource<Int, LinkEntity>

    @Query("SELECT * from links WHERE id_wish = :id")
    abstract fun buscarLinksPorId(id: Long): PagingSource<Int, LinkEntity>

    @Insert
    abstract suspend fun criarLink(link: LinkEntity)

    @Delete
    abstract suspend fun deleteLink(link: LinkEntity)
}

