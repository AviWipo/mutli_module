package com.lloyds.feature_nowplaying.di

import com.example.domain.usecase.NowPlayingUseCase
import com.lloyds.feature_nowplaying.NowPlayingProvider
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
    fun provideNowPlayingProvider(
        nowPlayingUseCase: NowPlayingUseCase
    ): NowPlayingProvider {
        return NowPlayingProvider(nowPlayingUseCase)
    }
}