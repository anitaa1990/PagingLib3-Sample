package com.an.paginglib3_sample.data

import com.an.paginglib3_sample.BuildConfig
import com.an.paginglib3_sample.api.NewsApiService
import com.an.paginglib3_sample.model.NewsApiResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchNews(
        query: String,
        nextPage: Long
    ): NewsApiResponse? {
        return try {
            val response = apiService.fetchFeed(
                q = query,
                apiKey = BuildConfig.api_key,
                pageSize = 20,
                page = nextPage
            )
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}