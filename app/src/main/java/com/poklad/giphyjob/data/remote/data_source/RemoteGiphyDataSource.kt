package com.poklad.giphyjob.data.remote.data_source

import com.poklad.giphyjob.data.common.data_sources.GiphyDataSource
import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.data.remote.GiphyApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteGiphyDataSource @Inject constructor(
    private val api: GiphyApi
) : GiphyDataSource {
    override fun getGifBy(id: String): Flow<GifEntity>  = flow {  }

    override fun getLiveDataGify(): Flow<List<GifEntity>> = flow { }

    override suspend fun getTrendingGifs(withOffset: Int): List<GifDataModel>? {
        val response = api.getTrendingGifs(offset = withOffset)
        return response?.data?.map {
            it.convertToGifDataModel()
        }
    }

    override suspend fun searchGifs(title: String): List<GifDataModel>? {
        val response = api.searchGifs(query = title)
        return response?.let {
            it.data.map {gifData->
                gifData.convertToGifDataModel()
            }
        }
    }
}