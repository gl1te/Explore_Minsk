package com.dev.exploreminsk.presentation.locations

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
import com.dev.exploreminsk.presentation.locations.components.LocationGrid
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun LocationsScreen(
    navigateToLocation: () -> Unit,
    navigateToDetails: (Place) -> Unit,
    state: State<LocationsScreenState>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                text = state.value.selectedCategory,
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
        Spacer(modifier = Modifier.height(28.dp))
        LocationGrid(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onPlaceClick = {
                navigateToDetails(it)
            },
            onPlaceDoubleClick = {},
            places = state.value.places
        )
    }
}