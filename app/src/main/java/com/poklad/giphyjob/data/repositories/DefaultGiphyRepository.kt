package com.poklad.giphyjob.data.repositories

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.data_source.CacheGifsDataSource
import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.data.remote.data_source.RemoteGiphyDataSource
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.utlis.connectivity.ConnectivityChecker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultGiphyRepository @Inject constructor(
    private val remoteGiphyDataSource: RemoteGiphyDataSource,
    private val cacheGifsDataSource: CacheGifsDataSource,
    private val connectivityChecker: ConnectivityChecker
) : GiphyRepository {

    override suspend fun getAllGify(): List<GifDataModel> =
        cacheGifsDataSource.getTrendingGifs()
    override fun getGifBy(id: String): Flow<GifEntity> =
        cacheGifsDataSource.getGifBy(id)

    override fun getLiveDataGify(): Flow<List<GifEntity>> =
        cacheGifsDataSource.getLiveDataGify()

    override suspend fun getTrendingGifs(withOffset: Int): List<GifDataModel> {
        return if (connectivityChecker.isConnected()) {
            try {
                val gifs =
                    remoteGiphyDataSource.getTrendingGifs(withOffset)
                if (gifs != null) {
                    if (gifs.isNotEmpty()) {
                        cacheGifsDataSource.saveGifs(gifs, withOffset > 0)
                    }
                }
                gifs?.filterNot {
                    cacheGifsDataSource.getDeletedGifs().contains(it.id)
                } ?: cacheGifsDataSource.getTrendingGifs()
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
                remoteGiphyDataSource.searchGifs(title)?.filterNot {
                    cacheGifsDataSource.getDeletedGifs().contains(it.id)
                } ?: cacheGifsDataSource.searchGifs(title)
            } catch (e: Exception) {
                cacheGifsDataSource.searchGifs(title)
            }
        } else {
            cacheGifsDataSource.searchGifs(title)
        }
    }

    override suspend fun deleteGif(id: String) {
        cacheGifsDataSource.deleteGif(id)
    }
}
