package com.luczka.miquidorecruitment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.luczka.miquidorecruitment.ui.mappers.toUiState
import com.luczka.miquidorecruitment.domain.usecases.GetPagedPicsumImagesUseCase
import com.luczka.miquidorecruitment.ui.models.PicsumImageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPagedPicsumImagesUseCase: GetPagedPicsumImagesUseCase
) : ViewModel() {

    val pagedPicsumImages: Flow<PagingData<PicsumImageUiState>> = getPagedPicsumImagesUseCase()
        .map { pagingData ->
            pagingData.map { it.toUiState() }
        }
        .cachedIn(viewModelScope)
}