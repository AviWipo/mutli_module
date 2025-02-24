package com.example.domain.di

import com.example.domain.gateway.APIGateway
import com.example.domain.usecase.NowPlayingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideNowPlayingUseCase(
        apiGateway: APIGateway
    ): NowPlayingUseCase {
        return NowPlayingUseCase(apiGateway)
    }
}