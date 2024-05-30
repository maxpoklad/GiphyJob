package com.poklad.giphyjob.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poklad.giphyjob.data.local.dao.GifDao
import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.utlis.DatabaseConstants

@Database(
    entities = [GifEntity::class],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false
)
abstract class DefaultGifDatabase : RoomDatabase(), AppDatabase {
    abstract override fun getGifDao(): GifDao
}