package com.defaultapp.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.defaultapp.database.entity.DefaultEntity

@Dao
abstract class DefaultDao : EntityDao<DefaultEntity>() {

//    @Query("SELECT * from default")
//    abstract fun buscarDefault(): PagingSource<Int, DefaultEntity>
}

