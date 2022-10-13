package com.wishes.data.repository

import com.wishes.database.dao.SaldoDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.SaldoEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaldoRepository @Inject constructor(
    val saldoDao: SaldoDao,
    val transactionRunner: DatabaseTransactionRunner
) {
    fun buscarSaldo(): Flow<BigDecimal?>{
        return saldoDao.buscarSaldo()
    }

    suspend fun atualizarSaldo(saldo: BigDecimal){
        return saldoDao.atualizarSaldo(saldo)
    }

    suspend fun criarSaldo(saldo: SaldoEntity){
        return saldoDao.criarSaldo(saldo)
    }
}