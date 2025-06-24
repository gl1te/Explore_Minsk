package com.dev.exploreminsk.presentation.search_results.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.favorite.components.FavoritesItem
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun SearchList(
    places: List<Place>,
    onPlaceClick: (Place) -> Unit,
    onSeeAllClick: () -> Unit,
    showSeeAll: Boolean,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(items = places, key = { _, place -> place.id }) { _, place ->
            FavoritesItem(
                place = place,
                onPlaceClick = {
                    onPlaceClick(place)
                }
            )
        }
        if (showSeeAll) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = stringResource(R.string.see_all_label),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(R.color.custom_blue)
                        ),
                        modifier = Modifier.clickable {
                            onSeeAllClick()
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = {
                            onSeeAllClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "see_all_arrow",
                            modifier = Modifier.size(24.dp),
                            tint = colorResource(R.color.custom_blue)
                        )
                    }
                }
            }
        }
    }
}