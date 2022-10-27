package com.wishes.data.model

import androidx.room.ColumnInfo
import java.math.BigDecimal

data class SalarioDepositado(
    @ColumnInfo(name = "id"        ) val id: Long,
    @ColumnInfo(name = "data"      ) val data: String,
)