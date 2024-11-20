package com.an.paginglib3_sample.data

import com.an.paginglib3_sample.BuildConfig
import com.an.paginglib3_sample.api.NewsApiService
import com.an.paginglib3_sample.model.NewsApiResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
) {
    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 10
    }

    suspend fun fetchNews(
        query: String,
        nextPage: Long
    ): NewsApiResponse {
        return try {
            apiService.fetchFeed(
                q = query,
                apiKey = BuildConfig.api_key,
                pageSize = PAGE_SIZE,
                page = nextPage
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception(e)
        }
    }
}