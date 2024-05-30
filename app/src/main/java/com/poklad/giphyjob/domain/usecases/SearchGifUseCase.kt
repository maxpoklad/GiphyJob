package com.poklad.giphyjob.domain.usecases

import com.poklad.giphyjob.domain.mapper.GifDomainMapper
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import javax.inject.Inject

class SearchGifUseCase @Inject constructor(
    private val mapper: GifDomainMapper,
    private val repository: GiphyRepository
) {
    suspend fun searchGif(title: String): List<GifPresentationModel> {
        val gifs = repository.searchRepository(title)
        return gifs?.map { mapper.mapping(it) } ?: emptyList()
    }
}