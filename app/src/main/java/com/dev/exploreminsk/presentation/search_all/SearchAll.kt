package com.dev.exploreminsk.presentation.search_results

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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.common.PulseAnimation
import com.dev.exploreminsk.presentation.search_all.SearchAllScreenEvent
import com.dev.exploreminsk.presentation.search_all.SearchAllState
import com.dev.exploreminsk.presentation.search_results.components.SearchList
import com.dev.exploreminsk.presentation.search_results.components.SearchResultsEmptyScreen
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily


@Composable
fun SearchAll(
    navigateUp: () -> Unit,
    onPlaceClick: (Place) -> Unit,
    state: State<SearchAllState>,
    onEvent: (SearchAllScreenEvent)-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navigateUp() }
            )
            Text(
                text = stringResource(R.string.search_results_label),
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

        Spacer(modifier = Modifier.height(25.dp))

        TextField(
            value = state.value.searchQuery,
            onValueChange = { onEvent(SearchAllScreenEvent.OnSearchQueryChange(it)) },
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
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = colorResource(R.color.custom_light_gray),
                unfocusedContainerColor = colorResource(R.color.custom_light_gray)
            ),
            maxLines = 1,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(34.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val placesToShow = if (state.value.searchQuery.isNotBlank()) {
                state.value.searchedPlaces
            } else {
                state.value.places
            }

            when {
                state.value.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        PulseAnimation(modifier = Modifier.size(120.dp))
                    }
                }

                state.value.errorMessage != null -> {
                    Text(text = state.value.errorMessage ?: "Unknown error")
                }

                state.value.places.isEmpty() -> {
                    SearchResultsEmptyScreen()
                }

                else -> {
                    SearchList(
                        places = placesToShow,
                        onPlaceClick = onPlaceClick,
                        onSeeAllClick = {
                            //nothing
                        },
                        showSeeAll = false
                    )
                }
            }
        }

    }
}
