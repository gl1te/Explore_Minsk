package com.dev.exploreminsk.presentation.explore.components.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Preview
@Composable
fun RecommendedLocationItem() {
    Card(
        modifier = Modifier
            .height(135.dp)
            .width(175.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.rec_template_for_delete),
            contentDescription = "recommended_place",
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            text = "Explore Minsk",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                lineHeight = 6.sp,
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(start = 4.dp)
        )
        Row(
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.trending_up),
                contentDescription = "trending_up",
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "Hot Deal",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 10.sp,
                    lineHeight = 6.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFontFamily,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            )
        }
    }
}