package com.example.data

import com.example.data.remote.ApiService
import com.example.data.remote.RemoteDataSource
import com.example.data.remote.response.Movie
import com.example.data.remote.response.MoviesResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTests {

    private lateinit var apiService: ApiService
    private lateinit var dataSource: RemoteDataSource

    @Before
    fun setUp() {
        apiService = mockk()
        val movie = Movie(1, "originalTitle","overview", "posterPath", "title", 5.0)
        val response: Response<MoviesResponse> = Response.success(MoviesResponse(listOf(movie), 1, 1))
        coEvery { apiService.getPopular(any()) }.answers { response }
        coEvery { apiService.getUpcoming(any()) }.answers { response }
        coEvery { apiService.getNowPlaying(any()) }.answers { response }
        dataSource = RemoteDataSource(apiService)
    }

    @Test
    fun test_getPopularMovies() = runBlocking {
        dataSource.getPopularMovies(0)
        coVerify(exactly = 1) { apiService.getPopular(0) }
    }
    @Test
    fun test_getUpcoming() = runBlocking {
        dataSource.getUpcoming(0)
        coVerify(exactly = 1) { apiService.getUpcoming(0) }
    }
    @Test
    fun test_getNowPlaying() = runBlocking {
        dataSource.getNowPlaying(0)
        coVerify(exactly = 1) { apiService.getNowPlaying(0) }
    }

}