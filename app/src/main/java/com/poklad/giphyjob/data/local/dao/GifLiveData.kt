package com.poklad.giphyjob.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.poklad.giphyjob.data.local.models.GifEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface GifLiveData {
    @Query("SELECT * FROM gifentity")
    fun getAllGifs(): Flow<List<GifEntity>>

    @Query("SELECT * FROM gifentity where id==:id")
    fun getGifBy(id: String): Flow<GifEntity>
    @Delete
    suspend fun delete(gifEntity: GifEntity)
}