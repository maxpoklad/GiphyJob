package com.poklad.giphyjob.domain.usecases

import com.poklad.giphyjob.domain.mapper.GifDomainMapper
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import javax.inject.Inject

class GiphyUseCase @Inject constructor(
    private val repository: GiphyRepository,
    private val mapper: GifDomainMapper,
) {
    suspend fun getTrendingGifs(): List<GifPresentationModel> =
        repository.getTrendingGifs().filterNotNull().map { mapper.mapping(it) }
}