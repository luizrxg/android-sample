package com.wishes.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wishes.data.model.Link
import com.wishes.data.model.Wish
import java.math.BigDecimal

@Entity(tableName = "links")
data class LinkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id"     ) override var id: Long,
    @ColumnInfo(name = "id_wish") val id_wish: Long,
    @ColumnInfo(name = "link"   ) val link: String,
) : AppEntity

fun LinkEntity.asExternalModel() = Link(
    id,
    id_wish,
    link
)