package com.defaultapp.data.model

import androidx.room.ColumnInfo

data class Default(
    @ColumnInfo(name = "id"          ) val id: Long,
    @ColumnInfo(name = "nome"        ) val nome: String,
)