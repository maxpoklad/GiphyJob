package com.poklad.giphyjob.di

import android.content.Context
import com.poklad.giphyjob.domain.mapper.GifDomainMapper
import com.poklad.giphyjob.domain.repository.GiphyRepository
import com.poklad.giphyjob.domain.usecases.SearchGifUseCase
import com.poklad.giphyjob.utlis.connectivity.AndroidConnectivityChecker
import com.poklad.giphyjob.utlis.connectivity.ConnectivityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Singleton
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        AndroidConnectivityChecker(context)

    @Provides
    fun provideCurrencyMultipleUseCases(
        repository: GiphyRepository,
        mapper: GifDomainMapper,
    ) = SearchGifUseCase(
        repository = repository,
        mapper = mapper,
    )
}