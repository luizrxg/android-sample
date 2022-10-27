package com.wishes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wishes.data.model.Salario
import com.wishes.data.model.Wish
import java.math.BigDecimal

@Entity(tableName = "salario")
data class SalarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"   ) override var id: Long,
    @ColumnInfo(name = "valor"       ) val valor: BigDecimal,
    @ColumnInfo(name = "data"        ) val data: Int,
    @ColumnInfo(name = "data_criacao") val dataCriacao: String,
) : AppEntity

fun SalarioEntity.asExternalModel() = Salario(
    id,
    valor,
    data,
    dataCriacao
)