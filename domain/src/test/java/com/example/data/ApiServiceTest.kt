package com.example.data

import com.example.data.remote.ApiService
import com.example.data.remote.response.Movie
import com.example.data.remote.response.MoviesResponse
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Use the mock server URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getNowPlaying_returns_valid_response() = runBlocking {
        val movie = Movie(1, "originalTitle","overview", "posterPath", "title", 5.0)
        val mockResponse = MoviesResponse(listOf(movie), 1, 1)
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(mockResponse))
                .setResponseCode(200)
        )
        // When
        val response = apiService.getNowPlaying(0)
        // Then
        assertEquals(mockResponse, response.body())
    }

    @Test
    fun getNowPlaying_throws_exception_on_500_response() = runBlocking {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
        )
        // When
        val response = apiService.getNowPlaying(0)
        // Then
        assertEquals(500, response.code())
        assertEquals("Internal Server Error", response.errorBody()?.string())
    }
}