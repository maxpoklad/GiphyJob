package com.poklad.giphyjob.presentation.ui.screens.gif_details

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.poklad.giphyjob.R
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import com.poklad.giphyjob.presentation.ui.components.AnimatedGif
import com.poklad.giphyjob.utlis.PresentationConstants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailGifScreen(
    gifs: List<GifPresentationModel>,
    startIndex: Int,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = startIndex) {
        gifs.size
    }
    LaunchedEffect(pagerState.currentPage) {
        pagerState.animateScrollToPage(pagerState.currentPage)
    }

    Scaffold(
        topBar = {
            GifAppBar(
                title = gifs[pagerState.currentPage].title,
                navigateUp = navigateUp
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { page ->
            DetailedGif(
                context = LocalContext.current,
                gifItem = gifs[page],
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.middle_padding))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifAppBar(
    title: String,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
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
