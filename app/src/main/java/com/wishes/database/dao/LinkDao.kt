package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wishes.database.entity.LinkEntity

@Dao
abstract class LinkDao : EntityDao<LinkEntity>() {

    @Query("SELECT * from links")
    abstract fun buscarLinks(): PagingSource<Int, LinkEntity>

    @Insert
    abstract fun criarLink(link: LinkEntity)
}

