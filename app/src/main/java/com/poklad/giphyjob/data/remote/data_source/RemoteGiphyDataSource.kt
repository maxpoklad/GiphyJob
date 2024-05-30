package com.poklad.giphyjob.data.remote.data_source

import com.poklad.giphyjob.data.common.data_sources.GiphyDataSource
import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.remote.GiphyApi
import javax.inject.Inject

class RemoteGiphyDataSource @Inject constructor(
    private val api: GiphyApi
) : GiphyDataSource {
    override suspend fun getTrendingGifs(): List<GifDataModel>? {
        val response = api.getTrendingGifs()
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