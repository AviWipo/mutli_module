package com.example.domain

import com.example.domain.gateway.APIGateway
import com.example.domain.usecase.UpcomingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpcomingUseCaseTests {

    private lateinit var usecase: UpcomingUseCase
    private lateinit var apiGateway: APIGateway

    @Before
    fun setUp() {
        apiGateway = mockk()
        coEvery { apiGateway.getUpcomingMovies(any()) }.answers { mockk() }
        usecase = UpcomingUseCase(apiGateway)
    }

    @Test
    fun test_invoke() = runBlocking {
        usecase.invoke(0)
        coVerify(exactly = 1) { apiGateway.getUpcomingMovies(0) }
    }
}