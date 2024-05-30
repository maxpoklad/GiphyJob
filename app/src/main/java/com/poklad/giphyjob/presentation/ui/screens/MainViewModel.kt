package com.poklad.giphyjob.presentation.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poklad.giphyjob.domain.usecases.GetGifsUseCase
import com.poklad.giphyjob.domain.usecases.SearchGifUseCase
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import com.poklad.giphyjob.utlis.CoroutineDispatchersProvider
import com.poklad.giphyjob.utlis.logError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getGifsUseCase: GetGifsUseCase,
    private val searchGifUseCase: SearchGifUseCase,
    private val coroutineDispatcher: CoroutineDispatchersProvider
) : ViewModel() {
    private val _state = MutableStateFlow<TrendingGifsState>(TrendingGifsState.Loading)
    val state: StateFlow<TrendingGifsState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _state.update { TrendingGifsState.Error(throwable) }
            logError(throwable.message.toString())
        }

    init {
        fetchTrendingGifs()
        observeSearchQuery()
    }

    private fun fetchTrendingGifs() {
        viewModelScope.launch(coroutineExceptionHandler + coroutineDispatcher.ioDispatcher) {
            _state.emit(TrendingGifsState.Loading)
            try {
                val gifs = getGifsUseCase.getTrendingGifs()
                ensureActive()
                _state.emit(TrendingGifsState.Success(gifs))
            } catch (e: Exception) {
                _state.emit(TrendingGifsState.Error(e))
                e.localizedMessage?.let { logError(it) }
            }
        }
    }

    fun searchGifs(title: String) {
        _searchQuery.value = title
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch(coroutineDispatcher.ioDispatcher) {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isEmpty()) {
                        fetchTrendingGifs()
                    } else {
                        searchGifsInternal(query)
                    }
                }
        }
    }

    private suspend fun searchGifsInternal(query: String) {
        viewModelScope.launch(coroutineExceptionHandler + coroutineDispatcher.ioDispatcher) {
            _state.value = TrendingGifsState.Loading
            try {
                val gifs = searchGifUseCase.searchGif(query)
                _state.value = TrendingGifsState.Success(gifs)
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
