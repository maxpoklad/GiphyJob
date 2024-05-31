package com.poklad.giphyjob.data.local.database

import com.poklad.giphyjob.data.local.dao.DeletedGifDao
import com.poklad.giphyjob.data.local.dao.GifDao

interface AppDatabase {
    fun getGifDao(): GifDao
    fun getDeletedGifDao(): DeletedGifDao
}