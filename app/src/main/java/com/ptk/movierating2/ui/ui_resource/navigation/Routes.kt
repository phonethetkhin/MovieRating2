package com.ptk.movierating2.ui.ui_resource.navigation

sealed class Routes(val route: String) {
    data object SplashScreen : Routes("/splash_screen")
    data object LoginScreen : Routes("/login_screen")
    data object HomeScreen : Routes("/home_screen")
    data object DetailScreen : Routes("/detail_screen")
}