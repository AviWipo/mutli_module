package com.lloyds.feature_nowplaying.di

import com.example.domain.usecase.NowPlayingUseCase
import com.lloyds.feature_nowplaying.NowPlayingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNowPlayingRepository(
        nowPlayingUseCase: NowPlayingUseCase
    ): NowPlayingRepository {
        return NowPlayingRepository(nowPlayingUseCase)
    }
}