package com.luczka.miquidorecruitment.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.luczka.miquidorecruitment.data.local.PicsumDatabase
import com.luczka.miquidorecruitment.data.local.PicsumImageEntity
import com.luczka.miquidorecruitment.data.mappers.toEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PicsumRemoteMediator(
    private val picsumApiService: PicsumApiService,
    private val picsumDatabase: PicsumDatabase
) : RemoteMediator<Int, PicsumImageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PicsumImageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItemId = state.lastItemOrNull()?.id?.toIntOrNull()
                    if (lastItemId == null) {
                        1
                    } else {
                        (lastItemId + 1) / state.config.pageSize + 1
                    }
                }
            }

            Log.d("PicsumRemoteMediator", "Api called, loadKey: $loadKey")
            val picsumImageDtoList = picsumApiService.getPicsumImages(
                page = loadKey,
                limit = state.config.pageSize
            )

            val picsumImageEntityList = picsumImageDtoList.map { it.toEntity() }

            picsumDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    picsumDatabase.dao.clearAll()
                }
                picsumDatabase.dao.upsertAll(picsumImageEntityList)
            }

            val isEmptyResponse = picsumImageDtoList.isEmpty()
            val isNotFullResponse = picsumImageDtoList.size < state.config.pageSize

            MediatorResult.Success(endOfPaginationReached = isEmptyResponse || isNotFullResponse)
        } catch (e: IOException) {
            Log.d("PicsumRemoteMediator", "Error loading data: ${e.message}")
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.d("PicsumRemoteMediator", "Error loading data: ${e.message}")
            MediatorResult.Error(e)
        }
    }
}