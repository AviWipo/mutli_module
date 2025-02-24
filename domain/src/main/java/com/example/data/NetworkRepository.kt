package com.example.data


import com.example.domain.entity.Movies
import com.example.domain.gateway.APIGateway
import com.example.data.remote.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject


class NetworkRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): APIGateway {

    override suspend fun getPopularMovies(page: Int): Response<Movies> {
        return remoteDataSource.getPopularMovies(page)
    }

    override suspend fun getNowPlayingMovies(page: Int): Response<Movies> {
        return remoteDataSource.getNowPlaying(page)
    }

    override suspend fun getUpcomingMovies(page: Int): Response<Movies> {
        return remoteDataSource.getUpcoming(page)
    }

}