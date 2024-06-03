package com.poklad.giphyjob.domain.repository

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.data.remote.GiphyApi
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    fun getGifBy(id: String): Flow<GifEntity>
    fun getLiveDataGify(): Flow<List<GifEntity>>

    suspend fun getAllGify(): List<GifDataModel>
    suspend fun getTrendingGifs(withOffset: Int = GiphyApi.DEFAULT_OFFSET): List<GifDataModel>
    suspend fun searchRepository(title: String): List<GifDataModel>?
    suspend fun deleteGif(id: String)
}