package com.luczka.miquidorecruitment.domain.usecases

import com.luczka.miquidorecruitment.domain.models.PicsumImageModel
import com.luczka.miquidorecruitment.domain.repository.PicsumRepository

class GetPicsumImagesUseCase(private val picsumRepository: PicsumRepository) {

    suspend operator fun invoke(page: Int): Result<List<PicsumImageModel>> {
        return try {
            val picsumImageModelList = picsumRepository.getImages(page)
            Result.success(picsumImageModelList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}