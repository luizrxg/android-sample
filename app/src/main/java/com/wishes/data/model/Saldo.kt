package com.wishes.data.model

import androidx.room.ColumnInfo
import java.math.BigDecimal

data class Saldo(
    @ColumnInfo(name = "id"   ) val id: Long,
    @ColumnInfo(name = "saldo") val saldo: BigDecimal,
)