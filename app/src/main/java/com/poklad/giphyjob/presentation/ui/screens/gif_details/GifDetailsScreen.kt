package com.poklad.giphyjob.presentation.ui.screens.gif_details

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
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
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val context = LocalContext.current
    Scaffold(
        topBar = {
            GifAppBar(
                title = if (isPortrait) gifs[pagerState.currentPage].title else
                    stringResource(id = R.string.empty_string),
                navigateUp = navigateUp
            )
        }, modifier = modifier
    ) { paddingValues ->
        if (isPortrait) {
            PortraitPager(
                gifs = gifs,
                pagerState = pagerState,
                paddingValues = paddingValues,
                context = context
            )
        } else {
            LandscapePager(
                context = context,
                paddingValues = paddingValues,
                pagerState = pagerState,
                gifs = gifs
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitPager(
    gifs: List<GifPresentationModel>,
    pagerState: PagerState,
    paddingValues: PaddingValues,
    context: Context,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) { page ->
        DetailedGifPortrait(
            context = context,
            gifItem = gifs[page],
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.middle_padding))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LandscapePager(
    gifs: List<GifPresentationModel>,
    context: Context,
    pagerState: PagerState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) { page ->
        DetailedGifLandscape(
            context = context,
            gifItem = gifs[page],
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.middle_padding))
        )
    }
}
@Composable
fun DetailedGifLandscape(
    context: Context,
    gifItem: GifPresentationModel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxSize()) {
        DetailedGifPortrait(
            context = context,
            gifItem = gifItem,
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.middle_padding))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(dimensionResource(id = R.dimen.middle_padding))
        ) {
            Text(
                text = gifItem.title,
                maxLines = 2,
                style = MaterialTheme.typography.titleLarge,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(dimensionResource(id =R.dimen.big_padding)))
            Text(
                text = stringResource(id = R.string.author_format, gifItem.username),
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GifAppBar(
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
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    )
}

@Composable
private fun DetailedGifPortrait(
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
private fun DetailedGifPortraitPreview() {
    DetailedGifPortrait(
        context = LocalContext.current,
        gifItem = GifPresentationModel(
            id = "",
            url = PresentationConstants.TEST_GIF_URL,
            title = "",
            username = ""
        )
    )
}

@Preview(
    name = "Landscape",
    widthDp = 800,
    heightDp = 400,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun DetailGifLandscapePreview() {
    val gif = GifPresentationModel(
        id = "",
        url = PresentationConstants.TEST_GIF_URL,
        title = "Sample GIF",
        username = "Sample User"
    )
    DetailedGifLandscape(
        context = LocalContext.current,
        gifItem = gif
    )
}