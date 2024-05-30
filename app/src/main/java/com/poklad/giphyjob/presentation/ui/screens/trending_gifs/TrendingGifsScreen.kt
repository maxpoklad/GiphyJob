package com.poklad.giphyjob.presentation.ui.screens.trending_gifs

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.poklad.giphyjob.R
import com.poklad.giphyjob.presentation.model.GifPresentationModel
import com.poklad.giphyjob.presentation.ui.components.AnimatedGif
import com.poklad.giphyjob.presentation.ui.screens.MainViewModel
import com.poklad.giphyjob.presentation.ui.screens.TrendingGifsState
import com.poklad.giphyjob.utlis.PresentationConstants

@Composable
fun TrendingGifsScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    onGifClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val searchText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = { SearchBar(searchText = searchText) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TrendingGifsState.Loading -> CircularProgressIndicator()
                is TrendingGifsState.Success -> TrendingGifTable(
                    gifs = (state as TrendingGifsState.Success).gifs,
                    onGifClick = { index -> onGifClick(index) }
                )

                is TrendingGifsState.Error -> Text(text = "Error: ${(state as TrendingGifsState.Error).throwable.localizedMessage}")
            }
        }
    }
}

@Composable
private fun TrendingGifTable(
    gifs: List<GifPresentationModel>,
    onGifClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.middle_padding)),
        modifier = modifier
    ) {
        items(gifs, key = { it.id }) { gif ->
            GifItem(
                context = LocalContext.current,
                gifItem = gif,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.middle_padding))
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onGifClick(gifs.indexOf(gif)) }
            )
        }
    }
}

@Composable
private fun GifItem(
    context: Context,
    modifier: Modifier = Modifier,
    gifItem: GifPresentationModel
) {
    AnimatedGif(
        imageUrl = gifItem.url,
        context = context,
        contentDescription = gifItem.title,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun SearchBar(
    searchText: String,
    modifier: Modifier = Modifier,
    onSearchTextChanged: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = searchText,
        maxLines = 1,
        onValueChange = onSearchTextChanged,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.middle_padding))
    )
}

@Preview(showSystemUi = true)
@Composable
private fun TrendingGifTablePreview() {
    val list = List(20) { index ->
        GifPresentationModel(
            id = "id_$index",
            url = PresentationConstants.TEST_GIF_URL,
            title = "Gif $index",
            username = "User $index"
        )
    }
    TrendingGifTable(
        gifs = list, {})
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(searchText = "", onSearchTextChanged = {})
}
