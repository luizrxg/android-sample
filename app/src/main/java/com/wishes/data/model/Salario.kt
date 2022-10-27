package com.wishes.data.model

import androidx.room.ColumnInfo
import java.math.BigDecimal

data class Salario(
    @ColumnInfo(name = "id"          ) val id: Long,
    @ColumnInfo(name = "valor"       ) val valor: BigDecimal,
    @ColumnInfo(name = "data"        ) val data: Int,
    @ColumnInfo(name = "data_criacao") val dataCriacao: String,
)