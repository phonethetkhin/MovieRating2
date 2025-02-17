package com.ptk.movierating2.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ptk.movierating2.R
import com.ptk.movierating2.db.entity.MoviesEntity
import com.ptk.movierating2.ui.ui_resource.composables.AnimatedShimmer
import com.ptk.movierating2.ui.ui_resource.composables.NoConnectionDialog
import com.ptk.movierating2.ui.ui_resource.navigation.Routes
import com.ptk.movierating2.ui.ui_resource.theme.LightBlue
import com.ptk.movierating2.ui.ui_states.HomeUIStates
import com.ptk.movierating2.util.Constants
import com.ptk.movierating2.viewmodel.DetailViewModel
import com.ptk.movierating2.viewmodel.HomeViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
/**
 * FirstTime Install, will fetch data from api and insert into the database.
 * Will show the data from the local database unless user want to reload using swipe refresh.
 * Swipe refresh will fetch the data from the api again and insert it into the database.
 * Before insert data to the database, will wipe all data from the database first
 * Fetch PopularMovie, then fetch upcoming, then fetch details of all the movies inside database, then store.
 * The data used in the UIs/ States are the data from the database.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
) {
    val uiStates by homeViewModel.uiStates.collectAsState()

    LaunchedEffect(key1 = "") {
        homeViewModel.checkConnection()
    }
   
    NoConnectionDialog(
        showDialog = uiStates.isShowNoConnectionDialog,
        onDismissRequest = { homeViewModel.toggleIsShowNoConnectionDialog(false) }) {
        homeViewModel.checkConnection()

    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = uiStates.refreshing),
        onRefresh = { homeViewModel.onRefresh() },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightBlue),
                    title = {
                        Text(
                            "Home",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.ssp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.sdp),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {

                    }
                )
            }
        ) {

            if (!uiStates.isShowNoConnectionDialog) {
                if (uiStates.popularList.isNotEmpty() && uiStates.upcomingList.isNotEmpty()) {
                    HomeScreenContent(
                        navController,
                        it.calculateTopPadding().value,
                        uiStates,
                        homeViewModel,
                        detailViewModel
                    )
                } else {
                    Column(modifier = Modifier.padding(top = it.calculateTopPadding().value.dp)) {
                        repeat(7) {
                            AnimatedShimmer()
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun HomeScreenContent(
    navController: NavController,
    topMargin: Float,
    uiStates: HomeUIStates,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topMargin.dp)
    ) {
        item {
            RecommendedMoviesUI(
                "Popular Movies",
                uiStates.popularList,
                navController,
                homeViewModel,
                detailViewModel,
                uiStates,
            )
        }

        item {
            RecommendedMoviesUI(
                "Upcoming Movies",
                uiStates.upcomingList,
                navController,
                homeViewModel,
                detailViewModel,
                uiStates,
            )
        }

    }

}

@Composable
fun RecommendedMoviesUI(
    title: String,
    list: ArrayList<MoviesEntity>,
    navController: NavController,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    uiStates: HomeUIStates,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.sdp)
    ) {
        Text(
            text = title,
            fontSize = 16.ssp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.sdp)
        )

        MovieList(movies = list, navController, homeViewModel, detailViewModel, uiStates)
    }
}

@Composable
fun MovieList(
    movies: ArrayList<MoviesEntity>,
    navController: NavController,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    uiStates: HomeUIStates,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(movies) { movie ->
            MovieListItem(
                movie = movie,
                navController,
                homeViewModel,
                detailViewModel,
                uiStates
            )
        }
    }
}

@Composable
fun MovieListItem(
    movie: MoviesEntity,
    navController: NavController,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    uiStates: HomeUIStates,
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { navController.navigate(Routes.DetailScreen.route + "/movieId=${movie.id}") },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = LightBlue,
        ),
    ) {
        Column(
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_PATH_HELPER + movie.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title ?: "-",
                fontWeight = FontWeight.Bold,
                fontSize = 12.ssp,
                color = Color.White,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (movie.isFav) Color.Red else Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            homeViewModel.toggleIsFav(movie)
                            detailViewModel.toggleIsFav(movie.id, !movie.isFav)
                        }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${movie.voteAverage}%",
                    fontSize = 12.ssp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}




