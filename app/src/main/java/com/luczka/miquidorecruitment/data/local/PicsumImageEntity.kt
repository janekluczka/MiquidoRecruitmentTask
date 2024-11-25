package com.luczka.miquidorecruitment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PicsumImageEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String
)