package com.luczka.miquidorecruitment.domain.usecases

import androidx.paging.PagingData
import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import com.luczka.miquidorecruitment.domain.repository.PicsumRepository
import kotlinx.coroutines.flow.Flow

class GetPagedCachedPicsumImagesUseCase(private val picsumRepository: PicsumRepository){

    operator fun invoke(): Flow<PagingData<PicsumImageModel>> {
        return picsumRepository.getPagedCachedImages()
    }
}