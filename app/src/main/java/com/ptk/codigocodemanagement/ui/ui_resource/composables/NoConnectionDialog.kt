@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.codigocodemanagement.ui.ui_resource.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ptk.codigocodemanagement.R
import com.ptk.codigocodemanagement.ui.ui_resource.theme.Red
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@Composable
fun NoConnectionDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onRetry: () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest.invoke() }, properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {

            Card(

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.sdp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.sdp),
                        text = "Internet Connection unavailable",
                        fontSize = 16.ssp,
                        color = Red,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    val composition by rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(resId = R.raw.disconnected)
                    )
                    // render the animation
                    LottieAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.sdp), composition = composition,
                        iterations = LottieConstants.IterateForever // animate forever

                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                    Text(
                        text = "Please check your internet connection and try again...",
                        fontSize = 11.ssp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Button(onClick = {
                        onRetry.invoke()
                    }, modifier = Modifier.align(End)) {
                        Text("Retry")
                    }

                }

            }
        }
    }
}