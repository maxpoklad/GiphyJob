package com.poklad.giphyjob.presentation.ui.screens.gif_details

import androidx.lifecycle.ViewModel
import com.poklad.giphyjob.domain.usecases.GetGifsUseCase
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DetailGifViewModel @Inject constructor(
    private val getGifsUseCase: GetGifsUseCase,
) : ViewModel() {
    val gifFlow: List<GifPresentationModel> = runBlocking {
        getGifsUseCase.getAllGifs()
    }

}