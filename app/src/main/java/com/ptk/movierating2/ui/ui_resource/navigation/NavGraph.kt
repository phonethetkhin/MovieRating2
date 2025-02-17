package com.ptk.movierating2.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ptk.movierating2.ui.screen.DetailScreen
import com.ptk.movierating2.ui.screen.HomeScreen
import com.ptk.movierating2.viewmodel.DetailViewModel
import com.ptk.movierating2.viewmodel.HomeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController, homeViewModel, detailViewModel)
        }

        composable(
            route = Routes.DetailScreen.route + "/movieId={movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                },
            )
        ) { nav ->
            val movieId = nav.arguments?.getInt("movieId")!!
            DetailScreen(navController, movieId, homeViewModel)
        }


    }
}