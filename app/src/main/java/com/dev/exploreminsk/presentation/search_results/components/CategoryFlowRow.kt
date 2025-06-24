package com.dev.exploreminsk.presentation.search_results.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.presentation.explore.components.category.CategoryItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFlowRow(categories: List<Category>, selectedCategory: Category?, onCategorySelected: (Category)->Unit) {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(70.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        categories.forEach { category ->
            CategorySearchItem(
                text = category.name,
                isSelected = category == selectedCategory,
                onClick = {
                    onCategorySelected(category)
                }
            )
        }
    }
}