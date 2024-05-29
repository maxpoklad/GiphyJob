package com.poklad.giphyjob.di

import com.poklad.giphyjob.data.remote.GiphyApi
import com.poklad.giphyjob.data.remote.data_source.RemoteGiphyDataSource
import com.poklad.giphyjob.data.remote.interceptop.ApiKeyInterceptor
import com.poklad.giphyjob.data.repositories.DefaultGiphyRepository
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.utlis.ApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideGiphyApi(retrofit: Retrofit): GiphyApi = retrofit.create(GiphyApi::class.java)


    @Singleton
    @Provides
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(ApiConstants.API_KEY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRemoteGiphyDataSource(api: GiphyApi): RemoteGiphyDataSource =
        RemoteGiphyDataSource(api)

    @Provides
    @Singleton
    fun provideGiphyRepository(remoteGiphyDataSource: RemoteGiphyDataSource): GiphyRepository =
        DefaultGiphyRepository(remoteGiphyDataSource = remoteGiphyDataSource)
}