package com.poklad.giphyjob.domain.repository

import com.poklad.giphyjob.data.common.models.GifDataModel

interface GiphyRepository {
    suspend fun getTrendingGifs(): List<GifDataModel>
    suspend fun searchRepository(title: String): List<GifDataModel>?
}