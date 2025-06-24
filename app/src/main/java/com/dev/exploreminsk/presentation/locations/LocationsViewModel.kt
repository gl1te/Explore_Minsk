package com.dev.exploreminsk.presentation.locations

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.model.Category
import com.dev.exploreminsk.domain.usecase.place_usecase.PlaceUseCases
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val placesUseCase: PlaceUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(LocationsScreenState())
    val state = _state

    init {
        val encodedCategoryJson: String? = savedStateHandle.get<String>("categoryJson")
        val category = encodedCategoryJson?.let { Gson().fromJson(it, Category::class.java) }
        if (category != null) {
            _state.update {
                it.copy(
                    selectedCategory = category.name
                )
            }
            getPlacesByCategory(category)
        }
    }

    private fun getPlacesByCategory(category: Category) {
        viewModelScope.launch {
            placesUseCase.getAllPlacesByCategory(category)
                .onSuccess { places ->
                    _state.update {
                        it.copy(
                            places = places
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            errorMessage = error.message
                        )
                    }
                }
        }
    }

}