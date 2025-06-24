package com.dev.exploreminsk.presentation.explore.components.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.domain.model.Category

@Composable
fun CategoryRow(selectedCategory: Category, categories: List<Category>, onCategorySelected: (Category) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(start = 25.dp, end = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                text = category.name,
                isSelected = category.name == selectedCategory.name,
                onClick = {
                    onCategorySelected(category)
                }
            )
        }
    }
}