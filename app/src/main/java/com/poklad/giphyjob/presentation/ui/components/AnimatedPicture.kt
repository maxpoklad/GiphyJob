package com.poklad.giphyjob.presentation.ui.components

import android.content.Context
import android.os.Build
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Precision
import com.poklad.giphyjob.R
import com.poklad.giphyjob.utlis.PresentationConstants.TEST_GIF_URL


@Composable
fun AnimatedGif(
    imageUrl: String,
    context: Context,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val imageLoader = rememberImageLoader(context)

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .precision(Precision.EXACT)
        .placeholder(R.drawable.pic_placeholder)
        .error(R.drawable.ic_launcher_foreground)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier.clip(RoundedCornerShape(dimensionResource(id = R.dimen.middle_padding))),
        contentScale = contentScale,
        imageLoader = imageLoader
    )
}

@Composable
private fun rememberImageLoader(context: Context): ImageLoader =
    ImageLoader.Builder(context).components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()


@Preview(showSystemUi = true)
@Composable
private fun AnimatedGifPreview() {
    AnimatedGif(
        contentDescription = "",
        imageUrl = TEST_GIF_URL,
        context = LocalContext.current
    )
}
