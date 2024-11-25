package com.luczka.miquidorecruitment.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class DetailsViewModelState(
    val picsumImage: PicsumImageUiState,
    val showImage: Boolean = false
) {
    fun toUiState(): DetailsUiState {
        return DetailsUiState(
            picsumImage = picsumImage,
            showImage = showImage
        )
    }
}

@AssistedFactory
interface DetailsViewModelFactory {
    fun create(picsumImage: PicsumImageUiState): DetailsViewModel
}

@HiltViewModel(assistedFactory = DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted picsumImage: PicsumImageUiState
) : ViewModel() {

    private val viewModelState = MutableStateFlow(DetailsViewModelState(picsumImage))
    val uiState = viewModelState
        .map(DetailsViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = viewModelState.value.toUiState()
        )

    fun onAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.NavigateUp -> {}
            is DetailsAction.ShowImage -> showImage()
            is DetailsAction.HideImage -> hideImage()
        }
    }

    private fun showImage() {
        viewModelState.update {
            it.copy(showImage = true)
        }
    }

    private fun hideImage() {
        viewModelState.update {
            it.copy(showImage = false)
        }
    }
}