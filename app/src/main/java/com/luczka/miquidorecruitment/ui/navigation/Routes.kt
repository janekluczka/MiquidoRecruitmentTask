package com.luczka.miquidorecruitment.ui.navigation

import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import kotlinx.serialization.Serializable

data object Routes {

    @Serializable
    data object Main

    @Serializable
    data class Details(val picsumImage: PicsumImageUiState)
}