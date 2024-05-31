package com.poklad.giphyjob.data.remote

import com.poklad.giphyjob.data.remote.model.GiphyResponse
import com.poklad.giphyjob.utlis.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET(ApiConstants.TRENDING_GIFS)
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("bundle") bundle: String = DEFAULT_BUNDLE
    ): GiphyResponse?

    @GET(ApiConstants.SEARCH_GIFS)
    suspend fun searchGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = DEFAULT_OFFSET,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("lang") lang: String = DEFAULT_LANG,
        @Query("bundle") bundle: String = DEFAULT_BUNDLE
    ): GiphyResponse?

    companion object {
        const val DEFAULT_LIMIT = 50
        const val DEFAULT_OFFSET = 50
        const val DEFAULT_RATING = "pg"
        const val DEFAULT_LANG = "en"
        const val DEFAULT_BUNDLE = "messaging_non_clips"
    }

}