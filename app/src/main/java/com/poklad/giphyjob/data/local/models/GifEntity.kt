package com.poklad.giphyjob.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.poklad.giphyjob.data.common.models.GifDataModel

@Entity
data class GifEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo("url")
    override val url: String,
    @ColumnInfo("title")
    override val title: String,
    @ColumnInfo("username")
    override val username: String
) : GifDataModel