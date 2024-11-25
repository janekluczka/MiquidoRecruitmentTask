package com.luczka.miquidorecruitment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.luczka.miquidorecruitment.data.local.PicsumImageEntity
import com.luczka.miquidorecruitment.data.mappers.toModel
import com.luczka.miquidorecruitment.data.paging.PicsumPagingSource
import com.luczka.miquidorecruitment.data.remote.PicsumApiService
import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import com.luczka.miquidorecruitment.domain.repository.PicsumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PicsumRepositoryImpl(
    private val apiService: PicsumApiService,
    private val pager: Pager<Int, PicsumImageEntity>
) : PicsumRepository {

    override suspend fun getImages(page: Int): List<PicsumImageModel> {
        return withContext(Dispatchers.IO) {
            apiService.getPicsumImages(page = page, limit = PicsumApiService.PAGE_SIZE).map { it.toModel() }
        }
    }

    override fun getPagedImages(): Flow<PagingData<PicsumImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PicsumApiService.PAGE_SIZE,
                prefetchDistance = 1,
                initialLoadSize = PicsumApiService.PAGE_SIZE
            ),
            pagingSourceFactory = {
                PicsumPagingSource(apiService)
            }
        ).flow
    }

    override fun getPagedCachedImages(): Flow<PagingData<PicsumImageModel>> {
        return pager.flow.map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }
}