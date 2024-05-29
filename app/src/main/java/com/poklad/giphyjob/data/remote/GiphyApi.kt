package com.poklad.giphyjob.data.remote

import com.poklad.giphyjob.data.remote.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating: String = "pg",
        @Query("bundle") bundle: String = "messaging_non_clips"
    ): GiphyResponse?
}