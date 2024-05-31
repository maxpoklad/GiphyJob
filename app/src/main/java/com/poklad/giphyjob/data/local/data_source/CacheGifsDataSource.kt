package com.poklad.giphyjob.data.local.data_source

import com.poklad.giphyjob.data.common.data_sources.GiphyDataSource
import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.data.local.dao.DeletedGifDao
import com.poklad.giphyjob.data.local.dao.GifDao
import com.poklad.giphyjob.data.local.models.DeletedGifEntity
import com.poklad.giphyjob.data.local.models.GifEntity
import javax.inject.Inject

class CacheGifsDataSource @Inject constructor(
    private val gifDao: GifDao,
    private val deletedGifDao: DeletedGifDao
) : GiphyDataSource {
    override suspend fun getTrendingGifs(): List<GifDataModel> {
        return gifDao.getAllGifs()
    }

    override suspend fun searchGifs(title: String): List<GifDataModel>? {
        return gifDao.searchGifs(title)
    }

    suspend fun saveGifs(gifs: List<GifDataModel>) {
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