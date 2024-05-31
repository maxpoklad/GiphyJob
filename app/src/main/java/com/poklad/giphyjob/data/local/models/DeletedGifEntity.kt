package com.poklad.giphyjob.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_gifs")
data class DeletedGifEntity(
    @PrimaryKey val id: String
)