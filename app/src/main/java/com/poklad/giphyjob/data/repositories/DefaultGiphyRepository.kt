package com.poklad.giphyjob.data.repositories

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.data_source.CacheGifsDataSource
import com.poklad.giphyjob.data.remote.data_source.RemoteGiphyDataSource
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.utlis.connectivity.ConnectivityChecker
import javax.inject.Inject

class DefaultGiphyRepository @Inject constructor(
    private val remoteGiphyDataSource: RemoteGiphyDataSource,
    private val cacheGifsDataSource: CacheGifsDataSource,
    private val connectivityChecker: ConnectivityChecker
) : GiphyRepository {

    override suspend fun getTrendingGifs(): List<GifDataModel> {
        return if (connectivityChecker.isConnected()) {
            try {
                val gifs =
                    remoteGiphyDataSource.getTrendingGifs()
                if (gifs != null) {
                    if (gifs.isNotEmpty()) {
                        cacheGifsDataSource.saveGifs(gifs)
                    }
                }
                gifs ?: cacheGifsDataSource.getTrendingGifs()
            } catch (e: Exception) {
                cacheGifsDataSource.getTrendingGifs()
            }
        } else {
            cacheGifsDataSource.getTrendingGifs()
        }
    }

    override suspend fun searchRepository(title: String): List<GifDataModel>? {
        return if (connectivityChecker.isConnected()) {
            try {
                remoteGiphyDataSource.searchGifs(title) ?: cacheGifsDataSource.searchGifs(title)
            } catch (e: Exception) {
                cacheGifsDataSource.searchGifs(title)
            }
        } else {
            cacheGifsDataSource.searchGifs(title)
        }
    }
}
