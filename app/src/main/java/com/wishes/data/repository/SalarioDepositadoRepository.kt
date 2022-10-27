package com.wishes.data.repository

import com.wishes.data.model.SalarioDepositado
import com.wishes.database.dao.SalarioDepositadoDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.SalarioDepositadoEntity
import com.wishes.util.getMonthYear
import com.wishes.util.getYearMonth
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SalarioDepositadoRepository @Inject constructor(
    val salarioDepositadoDao: SalarioDepositadoDao,
    val transactionRunner: DatabaseTransactionRunner
) {

    suspend fun depositouEsteMes(): Boolean{
        return salarioDepositadoDao.buscarSalarioDepositado(getYearMonth()) != null
    }

    suspend fun buscarSalariosDepositados(): List<SalarioDepositado?>{
        return salarioDepositadoDao.buscarSalariosDepositados()
    }

    suspend fun buscarSalariosDepositados(data: String): List<SalarioDepositado?>{
        return salarioDepositadoDao.buscarSalariosDepositados(data)
    }

    suspend fun depositarSalario(salarioDepositado: SalarioDepositadoEntity){
        salarioDepositadoDao.depositarSalario(salarioDepositado)
    }
}