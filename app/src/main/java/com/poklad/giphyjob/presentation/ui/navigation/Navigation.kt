package com.poklad.giphyjob.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.poklad.giphyjob.R
import com.poklad.giphyjob.presentation.ui.screens.gif_details.DetailGifScreen
import com.poklad.giphyjob.presentation.ui.screens.trending_gifs.TrendingGifsScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "trending"
    ) {
        composable(route = "trending") {
            TrendingGifsScreen(
                onGifClick = {gifUrl ->
                    navController.navigate("gif_details/$gifUrl")
                }
            )
        }
        composable(
            route = "gif_details/{gifUrl}",
            arguments = listOf(navArgument("gifUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val gifUrl = backStackEntry.arguments?.getString("gifUrl")
            gifUrl?.let {
                DetailGifScreen(it)
            }
        }
    }
}

private enum class Screens(@StringRes val title: Int) {
    TRENDING_GIFS(title = R.string.tranding),
    DETAIL_GIF(title = R.string.details)
}