package com.luczka.miquidorecruitment.ui.util

import androidx.paging.PagingData
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Suppress("MemberVisibilityCanBePrivate")
object PreviewDataUtil {

    fun getPicsumImageUiState(id: String = "0"): PicsumImageUiState {
        return PicsumImageUiState(
            id = id,
            author = "John Doe",
            width = 100,
            height = 100,
            imageUrl = "https://picsum.photos/id/0/100/100",
            downloadUrl = "https://picsum.photos/id/0/100/100"
        )
    }

    fun getPicsumImageUiStateList(amount: Int): List<PicsumImageUiState> {
        return (0..<amount).map {
            getPicsumImageUiState(it.toString())
        }
    }

    fun getPicsumImageUiPagingDataFlow(amount: Int): Flow<PagingData<PicsumImageUiState>> {
        val items = getPicsumImageUiStateList(amount)
        return flowOf(PagingData.from(items))
    }

}