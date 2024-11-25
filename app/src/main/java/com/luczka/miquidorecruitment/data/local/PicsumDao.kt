package com.luczka.miquidorecruitment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PicsumDao {

    @Upsert
    suspend fun upsertAll(picsumImages: List<PicsumImageEntity>)

    @Query("SELECT * FROM picsumimageentity")
    fun pagingSource(): PagingSource<Int, PicsumImageEntity>

    @Query("DELETE FROM picsumimageentity")
    suspend fun clearAll()
}