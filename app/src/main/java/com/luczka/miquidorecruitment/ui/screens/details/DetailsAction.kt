package com.luczka.miquidorecruitment.ui.screens.details

sealed class DetailsAction {
    data object NavigateUp : DetailsAction()
    data object HideImage : DetailsAction()
    data object ShowImage: DetailsAction()
}