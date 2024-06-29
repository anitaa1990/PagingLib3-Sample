package com.an.paginglib3_sample.api

import com.an.paginglib3_sample.BaseUnitTest
import com.an.paginglib3_sample.BuildConfig
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class NewsApiServiceTest: BaseUnitTest() {
    private val mockWebServer = MockWebServer()
    private lateinit var apiService: NewsApiService

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(NewsApiService::class.java)
    }
    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("api_response_success.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch news list and check response Code 200 returned`() = runTest {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("api_response_success.json").content)

        mockWebServer.enqueue(response)

        // Act
        val  actualResponse = apiService.fetchFeed(
            "movies",
            BuildConfig.api_key,
            1,
            20
        )

        // Assert
        assertEquals(200, actualResponse.code())
        assertEquals("ok", actualResponse.body()?.status)
        assertEquals(20, actualResponse.body()?.articles?.size)
    }

    @Test
    fun `fetch news list with empty results and check response Code 200 returned`() = runTest {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("api_response_empty.json").content)

        mockWebServer.enqueue(response)

        // Act
        val  actualResponse = apiService.fetchFeed(
            "asasasasasa",
            BuildConfig.api_key,
            1,
            20
        )

        // Assert
        assertEquals(200, actualResponse.code())
        assertEquals("ok", actualResponse.body()?.status)
        assertEquals(0, actualResponse.body()?.articles?.size)
        assertEquals(0L, actualResponse.body()?.totalResults)
    }

    @Test
    fun `fetch news list without internet and check response Code 400 returned`() = runTest {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody("{ \"error\": \"error\"}")

        mockWebServer.enqueue(response)

        // Act
        val  actualResponse = apiService.fetchFeed(
            "movies",
            BuildConfig.api_key,
            1,
            20
        )
        println(response.toString())

        // Assert
        assertEquals(400, actualResponse.code())
        assertNull(actualResponse.body())
        assertNotNull(actualResponse.errorBody())
    }
}