package com.wishes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wishes.data.model.Link
import com.wishes.data.model.Saldo
import com.wishes.data.model.Wish
import java.math.BigDecimal

@Entity(tableName = "saldo")
data class SaldoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"   ) override var id: Long,
    @ColumnInfo(name = "saldo") val saldo: BigDecimal,
) : AppEntity

fun SaldoEntity.asExternalModel() = Saldo(
    id,
    saldo
)