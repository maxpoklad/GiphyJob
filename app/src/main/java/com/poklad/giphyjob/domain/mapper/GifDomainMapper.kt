package com.poklad.giphyjob.domain.mapper

import com.poklad.giphyjob.data.common.models.GifDataModel
import com.poklad.giphyjob.domain.base.Mapper
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import javax.inject.Inject

class GifDomainMapper @Inject constructor() : Mapper<GifDataModel, GifPresentationModel> {
    override fun mapping(data: GifDataModel): GifPresentationModel = GifPresentationModel(
        id = data.id,
        url = data.url,
        title = data.title,
        username = data.username
    )
}