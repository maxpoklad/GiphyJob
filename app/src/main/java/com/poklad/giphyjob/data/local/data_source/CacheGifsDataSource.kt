package com.poklad.giphyjob.data.local.data_source

import com.poklad.giphyjob.data.common.data_sources.GiphyDataSource
import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.dao.DeletedGifDao
import com.poklad.giphyjob.data.local.dao.GifDao
import com.poklad.giphyjob.data.local.dao.GifLiveData
import com.poklad.giphyjob.data.local.models.DeletedGifEntity
import com.poklad.giphyjob.data.local.models.GifEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheGifsDataSource @Inject constructor(
    private val gifDao: GifDao,
    private val liveDataDao: GifLiveData,
    private val deletedGifDao: DeletedGifDao
) : GiphyDataSource {
    override fun getGifBy(id: String): Flow<GifEntity> =
        liveDataDao.getGifBy(id)

    override fun getLiveDataGify(): Flow<List<GifEntity>> =
        liveDataDao.getAllGifs()

    override suspend fun getTrendingGifs(withOffset: Int): List<GifDataModel> {
        return gifDao.getAllGifs()
    }

    override suspend fun searchGifs(title: String): List<GifDataModel>? {
        return gifDao.searchGifs(title)
    }

    suspend fun saveGifs(gifs: List<GifDataModel>, isNextPage: Boolean = false) {
        if (isNextPage.not())
            gifDao.clearGifs()
        gifDao.insertAll(gifs.map {
            GifEntity(
                id = it.id,
                url = it.url,
                title = it.title,
                username = it.username
            )
        })
    }

    suspend fun deleteGif(id: String) {
        deletedGifDao.insertDeletedGif(deletedGif = DeletedGifEntity(id))
        gifDao.deleteGif(id)
    }

    suspend fun getDeletedGifs(): List<String> {
        return deletedGifDao.getDeletedGifs()
    }
}