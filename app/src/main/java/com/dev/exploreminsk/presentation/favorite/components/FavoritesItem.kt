package com.dev.exploreminsk.presentation.favorite.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun FavoritesItem(place: Place, onPlaceClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onPlaceClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 15.dp, top = 10.dp, bottom = 10.dp)

        ) {
            AsyncImage(
                model = place.imageLink,
                contentScale = ContentScale.Crop,
                contentDescription = "favorite_item",
                modifier = Modifier
                    .width(55.dp)
                    .height(75.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontFamily = montserratFontFamily,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = place.category.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.custom_gray),
                        fontFamily = montserratFontFamily,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}