package com.luczka.miquidorecruitment.data.mappers

import com.luczka.miquidorecruitment.data.local.PicsumImageEntity
import com.luczka.miquidorecruitment.data.remote.dto.PicsumImageDto
import com.luczka.miquidorecruitment.domain.models.PicsumImageModel

fun PicsumImageDto.toEntity(): PicsumImageEntity {
    return PicsumImageEntity(
        id = id,
        author = author,
        width = width,
        height = height,
        url = url,
        downloadUrl = downloadUrl
    )
}

fun PicsumImageDto.toModel(): PicsumImageModel {
    return PicsumImageModel(
        id = id,
        author = author,
        width = width,
        height = height,
        imageUrl = url,
        downloadUrl = downloadUrl
    )
}

fun PicsumImageEntity.toModel(): PicsumImageModel {
    return PicsumImageModel(
        id = id,
        author = author,
        width = width,
        height = height,
        imageUrl = url,
        downloadUrl = downloadUrl
    )
}

