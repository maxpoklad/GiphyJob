package com.poklad.giphyjob.di

import android.content.Context
import androidx.room.Room
import com.poklad.giphyjob.data.local.dao.GifDao
import com.poklad.giphyjob.data.local.data_source.CacheGifsDataSource
import com.poklad.giphyjob.data.local.database.AppDatabase
import com.poklad.giphyjob.data.local.database.DefaultGifDatabase
import com.poklad.giphyjob.data.remote.GiphyApi
import com.poklad.giphyjob.data.remote.data_source.RemoteGiphyDataSource
import com.poklad.giphyjob.data.remote.interceptop.ApiKeyInterceptor
import com.poklad.giphyjob.data.repositories.DefaultGiphyRepository
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.utlis.ApiConstants
import com.poklad.giphyjob.utlis.DatabaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideCacheGifsDataSource(dao: GifDao): CacheGifsDataSource =
        CacheGifsDataSource(dao)
    @Provides
    @Singleton
    fun provideGifDao(database: AppDatabase): GifDao = database.getGifDao()
    @Provides
    @Singleton
    fun provideGiphyRepository(
        remoteGiphyDataSource: RemoteGiphyDataSource,
        cacheGifsDataSource: CacheGifsDataSource
    ): GiphyRepository =
        DefaultGiphyRepository(
            remoteGiphyDataSource = remoteGiphyDataSource,
            cacheGifsDataSource = cacheGifsDataSource
        )

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, DefaultGifDatabase::class.java, DatabaseConstants.DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

}