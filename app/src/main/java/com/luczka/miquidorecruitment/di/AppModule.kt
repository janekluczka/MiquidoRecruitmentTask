package com.luczka.miquidorecruitment.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.luczka.miquidorecruitment.data.local.PicsumDatabase
import com.luczka.miquidorecruitment.data.local.PicsumImageEntity
import com.luczka.miquidorecruitment.data.remote.PicsumApiService
import com.luczka.miquidorecruitment.data.remote.PicsumRemoteMediator
import com.luczka.miquidorecruitment.data.repository.PicsumRepositoryImpl
import com.luczka.miquidorecruitment.domain.repository.PicsumRepository
import com.luczka.miquidorecruitment.domain.usecases.GetPagedCachedPicsumImagesUseCase
import com.luczka.miquidorecruitment.domain.usecases.GetPagedPicsumImagesUseCase
import com.luczka.miquidorecruitment.domain.usecases.GetPicsumImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePicsumDatabase(@ApplicationContext context: Context): PicsumDatabase {
        return Room.databaseBuilder(
            context,
            PicsumDatabase::class.java,
            "picsum_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(PicsumApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePicsumApiService(retrofit: Retrofit): PicsumApiService {
        return retrofit.create(PicsumApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePicsumImagePager(
        picsumDatabase: PicsumDatabase,
        picsumApiService: PicsumApiService
    ): Pager<Int, PicsumImageEntity> {
        return Pager(
            config = PagingConfig(pageSize = PicsumApiService.PAGE_SIZE),
            remoteMediator = PicsumRemoteMediator(
                picsumDatabase = picsumDatabase,
                picsumApiService = picsumApiService
            ),
            pagingSourceFactory = {
                picsumDatabase.dao.pagingSource()
            }
        )
    }


    @Provides
    @Singleton
    fun providePicsumRepository(
        picsumApiService: PicsumApiService,
        intPicsumImagePager: Pager<Int, PicsumImageEntity>
    ): PicsumRepository {
        return PicsumRepositoryImpl(picsumApiService, intPicsumImagePager)
    }

    @Provides
    @Singleton
    fun provideGetPicsumImagesUseCase(picsumRepository: PicsumRepository): GetPicsumImagesUseCase {
        return GetPicsumImagesUseCase(picsumRepository)
    }

    @Provides
    @Singleton
    fun provideGetPagedCachedPicsumImagesUseCase(picsumRepository: PicsumRepository): GetPagedCachedPicsumImagesUseCase {
        return GetPagedCachedPicsumImagesUseCase(picsumRepository)
    }

    @Provides
    @Singleton
    fun provideGetPagedPicsumImagesUseCase(picsumRepository: PicsumRepository): GetPagedPicsumImagesUseCase {
        return GetPagedPicsumImagesUseCase(picsumRepository)
    }
}