package com.luczka.miquidorecruitment.data.remote

import com.luczka.miquidorecruitment.data.remote.dto.PicsumImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PicsumApiService {

    @GET("v2/list")
    suspend fun getPicsumImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PicsumImageDto>

    companion object {
        const val BASE_URL = "https://picsum.photos/"
        const val PAGE_SIZE = 20
    }
}