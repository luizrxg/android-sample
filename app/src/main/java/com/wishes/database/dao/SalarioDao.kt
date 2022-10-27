package com.wishes.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wishes.data.model.Salario
import com.wishes.data.model.Wish
import com.wishes.database.entity.SalarioEntity
import com.wishes.database.entity.WishEntity
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
abstract class SalarioDao : EntityDao<SalarioEntity>() {

    @Query("SELECT * FROM salario")
    abstract suspend fun buscarSalario(): Salario?

    @Query("UPDATE salario SET valor = :value, data = :data")
    abstract suspend fun atualizarSalario(value: BigDecimal, data: Int)

    @Insert
    abstract suspend fun criarSalario(salario: SalarioEntity)

    @Query("DELETE FROM salario")
    abstract suspend fun delete()
}

