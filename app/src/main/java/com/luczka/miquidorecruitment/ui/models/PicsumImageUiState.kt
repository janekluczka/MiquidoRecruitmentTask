package com.luczka.miquidorecruitment.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PicsumImageUiState(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String,
    val downloadUrl: String
) : Parcelable