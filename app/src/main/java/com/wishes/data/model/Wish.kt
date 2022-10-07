package com.wishes.data.model

import androidx.room.ColumnInfo
import java.math.BigDecimal

data class Wish(
    @ColumnInfo(name = "id"        ) val id: Long,
    @ColumnInfo(name = "nome"      ) val nome: String,
    @ColumnInfo(name = "preco"     ) val preco: BigDecimal,
    @ColumnInfo(name = "prioridade") val prioridade: Int,
    @ColumnInfo(name = "comprado"  ) val comprado: Boolean,
    @ColumnInfo(name = "data"      ) val data: String,
)