package com.example.data.di

import com.example.data.NetworkRepository
import com.example.data.remote.ApiService
import com.example.data.remote.RemoteDataSource
import com.example.domain.gateway.APIGateway
import com.example.domain.usecase.NowPlayingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAPIGateway(
        remoteDataSource: RemoteDataSource
    ): APIGateway {
        return NetworkRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService
    ): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiService.BASE_URL)
        .build()
        .create(ApiService::class.java)

}