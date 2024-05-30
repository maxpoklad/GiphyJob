package com.poklad.giphyjob.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.poklad.giphyjob.data.local.models.GifEntity

@Dao
interface GifDao {
    @Query("SELECT * FROM gifentity")
    suspend fun getAllGifs(): List<GifEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gifs: List<GifEntity>)

    @Query("DELETE FROM gifentity")
    suspend fun clearGifs()

    @Query("SELECT * FROM gifentity WHERE title LIKE '%' || :title || '%'")
    suspend fun searchGifs(title: String): List<GifEntity>?
}