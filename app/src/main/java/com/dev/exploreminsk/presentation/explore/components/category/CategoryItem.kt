package com.dev.exploreminsk.presentation.explore.components.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun CategoryItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        colorResource(R.color.custom_light_gray)
    } else {
        Color.Transparent
    }

    val textColor = if (isSelected) {
        colorResource(R.color.custom_blue)
    } else {
        Color.Gray
    }

    val fontWeight = if(isSelected) {
        FontWeight.SemiBold
    } else {
        FontWeight.Medium
    }

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .clip(CircleShape)
            .clickable { onClick() },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),


    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = montserratFontFamily,
                fontWeight = fontWeight, fontSize = 14.sp
            ),

        )
    }
}
