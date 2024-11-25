package com.luczka.miquidorecruitment.ui.mappers

import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState

fun PicsumImageModel.toUiState(): PicsumImageUiState {
    return PicsumImageUiState(
        id = id,
        author = author,
        width = width,
        height = height,
        imageUrl = imageUrl,
        downloadUrl = downloadUrl
    )
}