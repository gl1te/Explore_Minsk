package com.dev.exploreminsk.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.common.PulseAnimation
import com.dev.exploreminsk.presentation.explore.components.category.CategoryRow
import com.dev.exploreminsk.presentation.explore.components.location.LocationPopularRow
import com.dev.exploreminsk.presentation.explore.components.location.RecommendedLocationRow
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun ExploreScreen(
    navigateToDetails: (Place) -> Unit,
    navigateToSearchResults: () -> Unit,
    onSeeAllClick: (Category) -> Unit,
    state: State<ExploreScreenState>,
    onEvent: (ExploreScreenEvent) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
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
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        fontFamily = montserratFontFamily,
                        fontSize = 14.sp
                    )
                )
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.explore_label),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Normal,
                            fontFamily = montserratFontFamily,
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = stringResource(R.string.minsk_lower_label),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium,
                            fontSize = 32.sp,
                            fontFamily = montserratFontFamily,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.find_things_to_do_label),
                                    color = Color.Gray,
                                    fontFamily = montserratFontFamily,
                                    fontSize = 13.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .size(20.dp),
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = "search",
                                    tint = Color.Gray
                                )
                            },
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = colorResource(R.color.custom_light_gray),
                                unfocusedContainerColor = colorResource(R.color.custom_light_gray)
                            ),
                            maxLines = 1,
                            singleLine = true,
                            readOnly = true
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    navigateToSearchResults()
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                CategoryRow(
                    categories = state.value.categories,
                    onCategorySelected = {
                        onEvent(ExploreScreenEvent.OnCategorySelected(category = it))
                    },
                    selectedCategory = state.value.selectedCategory
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.popular_label),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 18.sp,
                                lineHeight = 6.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = montserratFontFamily,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Text(
                            text = stringResource(R.string.see_all_label),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 12.sp,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                color = colorResource(R.color.custom_blue)
                            ),
                            modifier = Modifier.clickable {
                                onSeeAllClick(state.value.selectedCategory)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                LocationPopularRow(
                    state.value.popularPlaces,
                    onPlaceClick = {
                        navigateToDetails(it)
                    },
                    onPlaceDoubleClick = {
                        onEvent(ExploreScreenEvent.OnPlaceDoubleClick(it))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.recommended_label),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 18.sp,
                        lineHeight = 6.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                RecommendedLocationRow()
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

