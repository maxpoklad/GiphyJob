package com.poklad.giphyjob.presentation.ui.screens.trending_gifs

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
    val searchQuery by viewModel.searchQuery.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val touchOutsideModifier = Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    }
    val orientation = LocalConfiguration.current.orientation
    val columns = if (orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchBar(
                searchText = searchQuery,
                onSearchTextChanged = { viewModel.searchGifs(it) }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .then(touchOutsideModifier),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TrendingGifsState.Loading -> CircularProgressIndicator()
                is TrendingGifsState.Success -> TrendingGifTable(
                    columns = columns,
                    gifs = (state as TrendingGifsState.Success).gifs,
                    onGifClick = { index ->
                        onGifClick(index)
                    },
                    onGifDelete = {
                        viewModel.deleteGif(it)
                    }
                )

                is TrendingGifsState.Error -> Text(text = "Error: ${(state as TrendingGifsState.Error).throwable.localizedMessage}")
            }
        }
    }
}

@Composable
private fun TrendingGifTable(
    gifs: List<GifPresentationModel>,
    columns: Int,
    onGifDelete: (String) -> Unit,
    onGifClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.middle_padding)),
        modifier = modifier
    ) {
        items(gifs, key = { it.id }) { gif ->
            GifItem(
                context = LocalContext.current,
                gifItem = gif,
                onGifDelete = onGifDelete,
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
    onGifDelete: (String) -> Unit,
    gifItem: GifPresentationModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.middle_padding))
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AnimatedGif(
            imageUrl = gifItem.url,
            context = context,
            contentDescription = gifItem.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { onGifDelete(gifItem.id) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete),
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
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
        onValueChange = {
            onSearchTextChanged(it)
        },
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
        gifs = list, onGifClick = {}, columns = 2, onGifDelete = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(searchText = "", onSearchTextChanged = {})
}
