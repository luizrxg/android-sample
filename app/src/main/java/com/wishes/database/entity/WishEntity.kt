package com.wishes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wishes.data.model.Wish
import java.math.BigDecimal

@Entity(tableName = "wishes")
data class WishEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"        ) override var id: Long,
    @ColumnInfo(name = "nome"      ) val nome: String,
    @ColumnInfo(name = "preco"     ) val preco: BigDecimal,
    @ColumnInfo(name = "prioridade") val prioridade: Int,
    @ColumnInfo(name = "comprado"  ) val comprado: Boolean,
    @ColumnInfo(name = "data"      ) val data: String,
) : AppEntity

fun WishEntity.asExternalModel() = Wish(
    id,
    nome,
    preco,
    prioridade,
    comprado,
    data,
)