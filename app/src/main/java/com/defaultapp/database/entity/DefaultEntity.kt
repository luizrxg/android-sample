package com.defaultapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.defaultapp.data.model.Default

@Entity(tableName = "default")
data class DefaultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"  ) override var id: Long,
    @ColumnInfo(name = "nome") val nome: String,
) : AppEntity

fun DefaultEntity.asExternalModel() = Default(
    id,
    nome,
)