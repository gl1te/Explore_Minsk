package com.dev.exploreminsk.presentation.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.domain.model.Place
import com.dev.exploreminsk.presentation.favorite.components.FavoriteEmptyScreen
import com.dev.exploreminsk.presentation.favorite.components.FavoritesList
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun FavoritesScreen(
    navigateToLocation: () -> Unit,
    navigateToDetails: (Place)-> Unit,
    onEvent: (FavoritesOnEvent) -> Unit,
    state: State<FavoritesScreenState>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp, )
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
                text = stringResource(R.string.favorites_label),
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
        Spacer(modifier = Modifier.height(32.dp))
        when {
            state.value.places.isEmpty() -> {
                FavoriteEmptyScreen()
            }

            state.value.errorMessage != null -> {

            }

            else -> {
                FavoritesList(
                    places = state.value.places,
                    onPlaceClick = {
                        navigateToDetails(it)
                    },
                    onDeleteClick = {
                        onEvent(FavoritesOnEvent.OnDeletePlace(it.id))
                    }
                )
            }
        }
    }
}