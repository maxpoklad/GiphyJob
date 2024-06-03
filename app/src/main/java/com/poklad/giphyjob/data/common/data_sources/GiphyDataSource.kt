package com.poklad.giphyjob.data.common.data_sources

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.data.remote.GiphyApi
import kotlinx.coroutines.flow.Flow

interface GiphyDataSource {
    fun getGifBy(id: String): Flow<GifEntity>
    fun getLiveDataGify(): Flow<List<GifEntity>>
    suspend fun getTrendingGifs(withOffset: Int = GiphyApi.DEFAULT_OFFSET): List<GifDataModel>?
    suspend fun searchGifs(title: String): List<GifDataModel>?
}