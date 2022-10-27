package com.wishes.data.repository

import com.wishes.data.model.Salario
import com.wishes.database.dao.SalarioDao
import com.wishes.database.dao.SaldoDao
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.entity.SalarioEntity
import com.wishes.database.entity.SaldoEntity
import com.wishes.util.getYearMonth
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SalarioRepository @Inject constructor(
    val salarioDao: SalarioDao,
    val transactionRunner: DatabaseTransactionRunner
) {
    suspend fun buscarSalario(): Salario? {
        return salarioDao.buscarSalario()
    }

    suspend fun atualizarSalario(
        value: BigDecimal,
        data: Int
    ) {
        val naoExisteSalario = buscarSalario() == null
        if (naoExisteSalario) {
            salarioDao.criarSalario(SalarioEntity(0, value, data, getYearMonth()))
        } else {
            salarioDao.atualizarSalario(value, data)
        }
    }

    suspend fun deleteSalario() {
        salarioDao.delete()
    }
}