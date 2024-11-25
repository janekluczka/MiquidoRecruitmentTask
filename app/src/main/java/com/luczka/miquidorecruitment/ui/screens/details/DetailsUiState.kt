package com.luczka.miquidorecruitment.ui.screens.details

import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState

data class DetailsUiState(
    val picsumImage: PicsumImageUiState,
    val showImage: Boolean = false
)