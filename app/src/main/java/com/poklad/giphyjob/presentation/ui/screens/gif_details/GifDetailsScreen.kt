package com.poklad.giphyjob.presentation.ui.screens.gif_details

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import com.poklad.giphyjob.presentation.ui.components.AnimatedGif
import com.poklad.giphyjob.utlis.PresentationConstants

@Composable
fun DetailGifScreen(
    gifUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {}
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            DetailedGif(
                context = context,
                gifItem = GifPresentationModel(
                    id = "",
                    url = gifUrl,
                    title = "",
                    username = ""
                )
            )
        }
    }
}

@Composable
private fun DetailedGif(
    context: Context,
    gifItem: GifPresentationModel,
    modifier: Modifier = Modifier
) {
    AnimatedGif(
        imageUrl = gifItem.url,
        context = context,
        contentDescription = gifItem.title,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}

@Preview
@Composable
private fun DetailedGifPreview() {
    DetailedGif(
        context = LocalContext.current,
        gifItem = GifPresentationModel(
            id = "",
            url = PresentationConstants.TEST_GIF_URL,
            title = "",
            username = ""
        )
    )
}
