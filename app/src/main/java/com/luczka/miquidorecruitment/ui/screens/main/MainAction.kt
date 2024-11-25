package com.luczka.miquidorecruitment.ui.screens.main

import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState

sealed class MainAction {
    data class NavigateToDetails(val picsumImage: PicsumImageUiState) : MainAction()
}