package com.poklad.giphyjob.domain.usecases

import com.poklad.giphyjob.domain.mapper.GifDomainMapper
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import javax.inject.Inject


class GetGifsUseCase @Inject constructor(
    private val repository: GiphyRepository,
    private val mapper: GifDomainMapper,
) {
    suspend fun getTrendingGifs(): List<GifPresentationModel> {
        return try {
            val gifs = repository.getTrendingGifs().map { mapper.mapping(it) }
            gifs
        } catch (e: Exception) {
            throw e
        }
    }
}