package com.dev.exploreminsk.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.common.PulseAnimation
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun ProfileScreen(
    navigateToEditProfile: () -> Unit,
    navigateToLocation: () -> Unit,
    state: State<ProfileScreenState>,
) {
    val context = LocalContext.current
    val imageUrl = state.value.user?.imageUrl

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .build()
    }

    val painter = rememberAsyncImagePainter(imageRequest)

    val isImageLoaded = painter.state is AsyncImagePainter.State.Success

    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PulseAnimation(modifier = Modifier.size(120.dp))
            }
        }

        state.value.errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.value.errorMessage.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navigateToLocation()
                            }
                    )
                    Text(
                        text = stringResource(R.string.profile_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = montserratFontFamily,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                }
                Spacer(
                    modifier = Modifier.height(50.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "profile_avatar",
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .alpha(if (isImageLoaded) 1f else 0.3f),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = state.value.user?.username ?: "User",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        fontFamily = montserratFontFamily,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    )
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = state.value.user?.email
                                        ?: stringResource(R.string.email_label),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 15.sp,
                                        fontFamily = montserratFontFamily,
                                        color = Color.Gray.copy(
                                            alpha = 0.7f
                                        )
                                    )
                                )
                            }

                            Icon(
                                painter = painterResource(R.drawable.edit),
                                "profile_edit",
                                modifier = Modifier
                                    .size(42.dp)
                                    .clickable {
                                        navigateToEditProfile()
                                    },
                                tint = colorResource(R.color.custom_blue)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(50.dp)
                    )
                    Text(
                        text = stringResource(R.string.rewards_label),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = montserratFontFamily,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                       verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.trailblazer),
                                contentDescription = "trailblazer_reward",
                                modifier = Modifier.size(130.dp)
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Image(
                                painter = painterResource(R.drawable.wander_lust),
                                contentDescription = "wander_lust_reward",
                                modifier = Modifier.size(130.dp)
                            )
                        }
                        Spacer(
                            modifier = Modifier.width(40.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Image(
                                painter = painterResource(R.drawable.route_master),
                                contentDescription = "route_master_reward",
                                modifier = Modifier
                                    .size(164.dp)
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            Image(
                                painter = painterResource(R.drawable.minsk_maven),
                                contentDescription = "trailblazer_reward",
                                modifier = Modifier.size(130.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}