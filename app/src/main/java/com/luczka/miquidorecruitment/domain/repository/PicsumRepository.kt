package com.luczka.miquidorecruitment.domain.repository

import androidx.paging.PagingData
import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import kotlinx.coroutines.flow.Flow

interface PicsumRepository {

    suspend fun getImages(page: Int): List<PicsumImageModel>

    fun getPagedImages(): Flow<PagingData<PicsumImageModel>>

    fun getPagedCachedImages(): Flow<PagingData<PicsumImageModel>>
}