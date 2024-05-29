package com.poklad.giphyjob.data.remote.model

import com.google.gson.annotations.SerializedName
import com.poklad.giphyjob.data.common.models.GifDataModel

data class GiphyResponse(
    @SerializedName("data")
    val data: List<GifData>
)

data class GifData(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("images")
    val images: Images
) {
    fun convertToGifDataModel() = object : GifDataModel {
        override val id = this@GifData.id
        override val url = this@GifData.images.original.url
        override val title = this@GifData.title
        override val username = this@GifData.username
    }
}

data class Images(
    @SerializedName("original")
    val original: Original
)

data class Original(
    @SerializedName("url")
    val url: String
)