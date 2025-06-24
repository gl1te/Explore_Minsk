package com.dev.exploreminsk.presentation.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    onPlaceClick: () -> Unit,
    onPlaceDoubleClick: () -> Unit,
    place: Place
) {
    Card(
        modifier = modifier
            .width(190.dp)
            .height(250.dp)
            .clip(RoundedCornerShape(24.dp))
            .combinedClickable(
                onClick = {
                    onPlaceClick()
                },
                onDoubleClick = {
                    onPlaceDoubleClick()
                }
            ),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = place.imageLink,
                contentDescription = "card_background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
            ) {
                Row(
                    modifier = Modifier
                        //.wrapContentWidth()
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.custom_gray)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFontFamily,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.custom_gray)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "star_icon",
                        modifier = Modifier
                            .padding(
                                bottom = 3.dp,
                                top = 3.dp,
                                start = 10.dp,
                            )
                            .size(17.dp),
                        tint = colorResource(R.color.custom_star_yellow)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${place.rating}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            fontFamily = montserratFontFamily,
                        ),
                        modifier = Modifier.padding(
                            bottom = 3.dp,
                            top = 3.dp,
                            end = 10.dp,
                        )
                    )
                }
            }
        }
    }
}