package com.poklad.giphyjob.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.poklad.giphyjob.data.local.models.DeletedGifEntity

@Dao
interface DeletedGifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeletedGif(deletedGif: DeletedGifEntity)

    @Query("SELECT id FROM deleted_gifs")
    suspend fun getDeletedGifs(): List<String>
}