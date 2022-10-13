package com.wishes.data.repository

import androidx.paging.PagingSource
import com.wishes.database.dao.LinkDao
import com.wishes.database.dao.WishDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.LinkEntity
import com.wishes.database.entity.WishEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepository @Inject constructor(
    val linkDao: LinkDao,
    val transactionRunner: DatabaseTransactionRunner
) {
    fun buscarLinks(): PagingSource<Int, LinkEntity> {
        return linkDao.buscarLinks()
    }

    fun buscarLinksPorId(id: Long): PagingSource<Int, LinkEntity> {
        return linkDao.buscarLinksPorId(id)
    }

    suspend fun criarLink(link: LinkEntity) {
        return linkDao.criarLink(link)
    }

    suspend fun deleteLink(link: LinkEntity) {
        return linkDao.deleteLink(link)
    }
}