package com.an.paginglib3_sample.data

import com.an.paginglib3_sample.BaseUnitTest
import com.an.paginglib3_sample.BuildConfig
import com.an.paginglib3_sample.api.NewsApiService
import com.an.paginglib3_sample.model.NewsApiResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import retrofit2.Response

class NewsRepositoryTest: BaseUnitTest() {
    private val newsApiService: NewsApiService = mock()
    private val repository = NewsRepository(newsApiService)

    private val expectedApiResponse = NewsApiResponse(
        status = "ok",
        totalResults = 20,
        articles = listOf()
    )

    private val emptyApiResponse = NewsApiResponse(
        status = "ok",
        totalResults = 0,
        articles = listOf()
    )

    private val defaultQuery = "movies"
    private val emptyResultQuery = "asasasasa"
    private val nextPage = 1L
    private val pageSize = 20
    private val apiKey = BuildConfig.api_key

    @Test
    fun `given no internet, when fetchNews, then result is null`() = runTest {
        val expectedErrorResponse: Response<NewsApiResponse> = Response.error(400, byteArrayOf().toResponseBody())
        `when`(newsApiService.fetchFeed(defaultQuery, apiKey, nextPage, pageSize)).thenReturn(
            expectedErrorResponse
        )

        val newsApiResponse = repository.fetchNews(defaultQuery, nextPage)
        verify(newsApiService).fetchFeed(defaultQuery, apiKey, nextPage, pageSize)
        assertNull(newsApiResponse)
    }

    @Test
    fun `given query is incorrect, when fetchNews, then result is exception`() = runTest {
        `when`(newsApiService.fetchFeed(
            q = emptyResultQuery,
            apiKey = apiKey,
            page = nextPage,
            pageSize = pageSize)).thenReturn(
            Response.success(emptyApiResponse)
        )

        val newsApiResponse = repository.fetchNews(emptyResultQuery, nextPage)
        verify(newsApiService).fetchFeed(
            emptyResultQuery,
            apiKey,
            nextPage,
            pageSize
        )
        assertNotNull(newsApiResponse)
        assertEquals(emptyApiResponse, newsApiResponse)
        assertEquals(emptyApiResponse.status, newsApiResponse!!.status)
        assertEquals(0, newsApiResponse.totalResults)
    }

    @Test
    fun `given query is correct, when fetchNews, then result is the expected`() = runTest {
        `when`(newsApiService.fetchFeed(
            q = defaultQuery,
            apiKey = apiKey,
            page = nextPage,
            pageSize = pageSize)).thenReturn(
            Response.success(expectedApiResponse)
        )

        val newsApiResponse = repository.fetchNews(defaultQuery, nextPage)
        verify(newsApiService).fetchFeed(
            defaultQuery,
            apiKey,
            nextPage,
            pageSize
        )
        assertNotNull(newsApiResponse)
        assertEquals(expectedApiResponse, newsApiResponse)
        assertEquals(expectedApiResponse.status, newsApiResponse!!.status)
        assertEquals(expectedApiResponse.totalResults, newsApiResponse.totalResults)
    }
}