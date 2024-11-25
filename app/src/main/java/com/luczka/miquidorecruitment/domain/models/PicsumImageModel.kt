package com.luczka.miquidorecruitment.domain.models

data class PicsumImageModel(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val imageUrl: String,
    val downloadUrl: String
)
