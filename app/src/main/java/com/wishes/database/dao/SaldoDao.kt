package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wishes.data.model.Wish
import com.wishes.database.entity.SaldoEntity
import com.wishes.database.entity.WishEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
abstract class SaldoDao : EntityDao<SaldoEntity>() {

    @Query("SELECT saldo from saldo")
    abstract fun buscarSaldo(): Flow<BigDecimal?>

    @Query("UPDATE saldo SET saldo = :saldo WHERE id = (select * from (select max(id) from saldo) as t)")
    abstract suspend fun atualizarSaldo(saldo: BigDecimal)

    @Insert
    abstract suspend fun criarSaldo(saldo: SaldoEntity)

}

