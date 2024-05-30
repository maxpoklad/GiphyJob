package com.poklad.giphyjob.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.poklad.giphyjob.R
import com.poklad.giphyjob.presentation.ui.screens.MainViewModel
import com.poklad.giphyjob.presentation.ui.screens.gif_details.DetailGifScreen
import com.poklad.giphyjob.presentation.ui.screens.trending_gifs.TrendingGifsScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.TRENDING_GIFS.name
    ) {
        composable(route = Screens.TRENDING_GIFS.name) {
            TrendingGifsScreen(
                onGifClick = { index ->
                    navController.navigate("${Screens.DETAIL_GIF.name}/$index")
                }
            )
        }
        composable(
            route = "${Screens.DETAIL_GIF.name}/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val gifs = viewModel.getGifs()
            DetailGifScreen(
                gifs = gifs,
                startIndex = index,
                navigateUp = { navController.navigateUp() })
        }
    }
}

private enum class Screens(@StringRes val title: Int) {
    TRENDING_GIFS(title = R.string.tranding),
    DETAIL_GIF(title = R.string.details)
}
