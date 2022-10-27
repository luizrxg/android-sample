package com.wishes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wishes.data.model.SalarioDepositado
import com.wishes.database.entity.SalarioDepositadoEntity

@Dao
abstract class SalarioDepositadoDao : EntityDao<SalarioDepositadoEntity>() {

    @Query("SELECT * FROM salarios_depositados")
    abstract suspend fun buscarSalariosDepositados(): List<SalarioDepositado?>

    @Query("SELECT * FROM salarios_depositados WHERE data >= :data")
    abstract suspend fun buscarSalariosDepositados(data: String): List<SalarioDepositado?>

    @Query("SELECT * FROM salarios_depositados WHERE data = :data")
    abstract suspend fun buscarSalarioDepositado(data: String): SalarioDepositado?

    @Insert
    abstract suspend fun depositarSalario(salarioDepositado: SalarioDepositadoEntity)
}

