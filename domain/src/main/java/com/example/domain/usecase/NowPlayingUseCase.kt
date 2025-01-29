package com.example.domain.usecase

import com.example.domain.entity.Movies
import com.example.domain.gateway.APIGateway
import retrofit2.Response
import javax.inject.Inject

class NowPlayingUseCase  @Inject constructor(
    private val apiGateway: APIGateway,
) {

    suspend operator fun invoke(page: Int): Response<Movies> {
        return apiGateway.getNowPlayingMovies(page)
    }
}