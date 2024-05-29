package com.poklad.giphyjob.data.common.data_sources

import com.poklad.giphyjob.data.common.models.GifDataModel

interface GiphyDataSource {
    suspend fun getTrendingGifs(): List<GifDataModel>?
}