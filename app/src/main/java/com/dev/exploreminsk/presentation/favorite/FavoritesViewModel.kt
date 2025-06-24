package com.dev.exploreminsk.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.exploreminsk.domain.usecase.room_usecase.RoomPlaceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val roomPlaceUseCases: RoomPlaceUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesScreenState())
    val state: StateFlow<FavoritesScreenState> = _state

    init {
        getPlaces()
    }

    fun onEvent(event: FavoritesOnEvent) {
        when (event) {
            is FavoritesOnEvent.OnDeletePlace -> {
                deletePlace(event.id)
            }
        }
    }

    private fun getPlaces() {
        try {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            roomPlaceUseCases.getPlacesUseCase().onEach { places ->
                _state.update {
                    it.copy(
                        places = places.reversed()
                    )
                }
            }.launchIn(viewModelScope)

        } catch (e: Exception) {
            _state.update {
                it.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    private fun deletePlace(id: Long) {
        viewModelScope.launch {
            try {
                roomPlaceUseCases.deletePlaceUseCase(id)
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }
            }
        }
    }

}