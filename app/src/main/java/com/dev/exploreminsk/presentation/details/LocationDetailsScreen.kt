package com.dev.exploreminsk.presentation.details

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun LocationDetailsScreen(navigateToLocation: () -> Unit, place: Place) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
        ) {
            AsyncImage(
                model = place.imageLink,
                contentDescription = "image_details",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clip(RoundedCornerShape(24.dp)),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .background(colorResource(R.color.custom_light_gray))
                    .clickable {
                        navigateToLocation()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "details_back",
                    tint = Color.Companion.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = place.name,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(14.dp),
                painter = rememberVectorPainter(image = Icons.Default.Star),
                contentDescription = "rating_icon",
                tint = colorResource(R.color.custom_star_yellow)

            )
            Text(
                text = "${place.rating} (${place.reviews} Reviews)",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.75f
                    )
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = place.description,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = montserratFontFamily,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                ),
                lineHeight = 16.sp
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.read_more_label),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.custom_blue),
            ),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, place.sourceLink.toUri())
                context.startActivity(intent)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        place.budget?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Budget",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 15.sp
                    )
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
        place.recommendedSightseeingTime?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Recommended\nsightseeing time",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 15.sp
                    )
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Address",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
            Text(
                text = place.address,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Phone",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
            Text(
                text = place.phone,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}