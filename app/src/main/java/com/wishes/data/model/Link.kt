package com.wishes.data.model

import androidx.room.ColumnInfo
import java.math.BigDecimal

data class Link(
    @ColumnInfo(name = "id"     ) val id: Long,
    @ColumnInfo(name = "id_wish") val id_wish: Long,
    @ColumnInfo(name = "link"   ) val link: String,
)