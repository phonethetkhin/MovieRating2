package com.ptk.movierating2.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ptk.movierating2.R
import com.ptk.movierating2.db.entity.MovieDetailEntity
import com.ptk.movierating2.ui.ui_resource.theme.LightBlue
import com.ptk.movierating2.ui.ui_states.DetailUIStates
import com.ptk.movierating2.util.Constants
import com.ptk.movierating2.util.convertMinutesToHoursAndMinutes
import com.ptk.movierating2.util.getConvertDate
import com.ptk.movierating2.viewmodel.DetailViewModel
import com.ptk.movierating2.viewmodel.HomeViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val uiStates by detailViewModel.uiStates.collectAsState()


    LaunchedEffect(key1 = "") {
        Log.e("DetailResponseMode1l", movieId.toString())

        detailViewModel.getMovieDetail(movieId)
    }
    Log.e("DetailResponseModel4", uiStates.detailResponseModel.toString())
    uiStates.detailResponseModel?.let {

        DetailScreenContent(
            navController = navController,
            uiStates = uiStates,
            movie = it,
            homeViewModel,
            detailViewModel,
        )
    }

}

@Composable
fun DetailScreenContent(
    navController: NavController,
    uiStates: DetailUIStates,
    movie: MovieDetailEntity,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Movie Cover Photo
        Box() {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.IMAGE_PATH_HELPER + movie.backdropPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "Movie Poster",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)

            )
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back Arrow Icon",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.sdp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${movie.title}",
                style = TextStyle(fontSize = 20.ssp),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = if (movie.isFav) Color.Red else Color.Gray,
                modifier = Modifier
                    .padding(end = 8.sdp)
                    .clickable {
                        detailViewModel.toggleIsFav(movie.id, !movie.isFav)
                    },
            )
            Text(
                text = "7.7%",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }

        Spacer(modifier = Modifier.height(8.sdp))

        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "${movie.productionCountries} | ${movie.releaseDate?.getConvertDate() ?: "-"}",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )

            Text(
                text = "${movie.voteCount} votes",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }

        Spacer(modifier = Modifier.height(8.sdp))

        val (hours, minutes) = movie.runtime!!.convertMinutesToHoursAndMinutes()
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "${hours}hr ${minutes}min | ${movie.genres}",
                style = TextStyle(fontSize = 14.ssp),
                color = LightBlue,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.sdp, end = 8.sdp)
            )
            Text(
                text = movie.spokenLanguages ?: "-",
                style = TextStyle(fontSize = 14.ssp),
                modifier = Modifier
                    .padding(end = 16.sdp)
            )

        }
        Spacer(modifier = Modifier.height(32.sdp))
        Text(
            text = "Movie Description",
            style = TextStyle(fontSize = 16.ssp),
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp, end = 8.sdp)
        )
        Spacer(modifier = Modifier.height(8.sdp))
        Text(
            text = "${movie.overview}",
            style = TextStyle(fontSize = 14.ssp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.sdp, end = 8.sdp, bottom = 8.sdp)
        )

    }
}



