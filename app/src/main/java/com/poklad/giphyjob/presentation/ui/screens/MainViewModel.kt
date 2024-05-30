package com.poklad.giphyjob.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poklad.giphyjob.domain.usecases.GiphyUseCase
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import com.poklad.giphyjob.utlis.CoroutineDispatchersProvider
import com.poklad.giphyjob.utlis.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GiphyUseCase,
    private val coroutineDispatcher: CoroutineDispatchersProvider
) : ViewModel() {
    private val _state = MutableStateFlow<TrendingGifsState>(TrendingGifsState.Loading)
    val state: StateFlow<TrendingGifsState> = _state.asStateFlow()

    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _state.value = TrendingGifsState.Error(throwable)
            logError(throwable.message.toString())
        }

    init {
        fetchTrendingGifs()
    }

    private fun fetchTrendingGifs() {
        viewModelScope.launch(coroutineExceptionHandler + coroutineDispatcher.ioDispatcher) {
            _state.value = TrendingGifsState.Loading
            try {
                val gifs = useCase.getTrendingGifs()
                _state.value = TrendingGifsState.Success(gifs = gifs)
            } catch (e: Exception) {
                _state.value = TrendingGifsState.Error(e)
                e.localizedMessage?.let { logError(it) }
            }
        }
    }

    fun getGifs(): List<GifPresentationModel> {
        return (_state.value as? TrendingGifsState.Success)?.gifs.orEmpty()
    }
}

sealed class TrendingGifsState {
    data object Loading : TrendingGifsState()
    data class Success(val gifs: List<GifPresentationModel>) : TrendingGifsState()
    data class Error(val throwable: Throwable) : TrendingGifsState()
}
