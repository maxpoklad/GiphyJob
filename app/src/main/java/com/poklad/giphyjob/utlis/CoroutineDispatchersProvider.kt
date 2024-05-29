package com.poklad.giphyjob.utlis

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchersProvider {
    val ioDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val unconfinedDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
}