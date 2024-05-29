package com.poklad.giphyjob.data.repositories

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.remote.data_source.RemoteGiphyDataSource
import com.poklad.giphyjob.domain.repository.GiphyRepository
import javax.inject.Inject

class DefaultGiphyRepository @Inject constructor(
    private val remoteGiphyDataSource: RemoteGiphyDataSource
) : GiphyRepository {

    override suspend fun getTrendingGifs(): List<GifDataModel?> {
        return remoteGiphyDataSource.getTrendingGifs()?: emptyList()
    }

}