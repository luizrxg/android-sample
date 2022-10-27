package com.wishes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wishes.data.model.Salario
import com.wishes.data.model.SalarioDepositado
import com.wishes.data.model.Wish
import java.math.BigDecimal

@Entity(tableName = "salarios_depositados")
data class SalarioDepositadoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"        ) override var id: Long,
    @ColumnInfo(name = "data"      ) val data: String,
) : AppEntity

fun SalarioDepositadoEntity.asExternalModel() = SalarioDepositado(
    id,
    data,
)