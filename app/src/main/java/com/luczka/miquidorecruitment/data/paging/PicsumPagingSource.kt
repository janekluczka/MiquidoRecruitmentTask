package com.luczka.miquidorecruitment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luczka.miquidorecruitment.data.mappers.toModel
import com.luczka.miquidorecruitment.data.remote.PicsumApiService
import com.luczka.miquidorecruitment.domain.models.PicsumError
import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class PicsumPagingSource(
    private val picsumApiService: PicsumApiService
) : PagingSource<Int, PicsumImageModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumImageModel> {
        return try {
            val currentPage = params.key ?: 1

            val picsumImageDtoList = picsumApiService.getPicsumImages(
                page = currentPage,
                limit = params.loadSize
            )
            val picsumImageModelList = picsumImageDtoList.map { it.toModel() }

            delay(1000) // Wait to show loading state

            LoadResult.Page(
                data = picsumImageModelList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (picsumImageModelList.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(
                when (e) {
                    is IOException -> PicsumError.NetworkError
                    is HttpException -> PicsumError.ServerError
                    else -> e
                }
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PicsumImageModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val nextKey = state.closestPageToPosition(anchorPosition)?.nextKey ?: 0
        return nextKey + 1
    }
}