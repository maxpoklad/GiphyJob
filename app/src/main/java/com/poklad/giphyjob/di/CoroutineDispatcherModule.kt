package com.poklad.giphyjob.di

import com.poklad.giphyjob.utlis.CoroutineDispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineDispatcherModule {

    @Provides
    fun provideDispatchersModule(): CoroutineDispatchersProvider =
        object : CoroutineDispatchersProvider {
            override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
            override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
            override val unconfinedDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
            override val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
        }

}