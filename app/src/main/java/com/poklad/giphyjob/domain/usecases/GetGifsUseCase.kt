package com.poklad.giphyjob.domain.usecases

import com.poklad.giphyjob.data.local.models.GifEntity
import com.poklad.giphyjob.data.remote.GiphyApi
import com.poklad.giphyjob.domain.mapper.GifDomainMapper
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetGifsUseCase @Inject constructor(
    private val repository: GiphyRepository,
    private val mapper: GifDomainMapper,
) {
    suspend fun getTrendingGifs(offset: Int = GiphyApi.DEFAULT_OFFSET): List<GifPresentationModel> {
        return try {
            val gifs = repository.getTrendingGifs(offset).map { mapper.mapping(it) }
            gifs
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAllGifs(): List<GifPresentationModel> =
        repository.getAllGify().map { mapper.mapping(it) }


    fun getGifBy(id: String): Flow<GifEntity> =
        repository.getGifBy(id)

    fun getLiveData(): Flow<List<GifEntity>> = repository.getLiveDataGify()
}