package com.example.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.remote.ApiService
import com.example.data.remote.RemoteDataSource
import com.example.domain.gateway.APIGateway
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/*
@HiltAndroidTest
class DataModuleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiGateway: APIGateway

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var apiService: ApiService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testApiGatewayInjection() {
        assertNotNull(apiGateway)
        assertTrue(apiGateway is NetworkRepository)
    }

    @Test
    fun testRemoteDataSourceInjection() {
        assertNotNull(remoteDataSource)
    }

    @Test
    fun testApiServiceInjection() {
        assertNotNull(apiService)
    }

    @Test
    fun testApiServiceBaseUrl() {
        // Use ApiService to ensure it's making calls to the correct base URL
        val baseUrl = ApiService.BASE_URL
        assertEquals("https://api.themoviedb.org/3/", baseUrl)
    }

}*/
