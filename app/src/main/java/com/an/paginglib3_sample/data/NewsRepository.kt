package com.an.paginglib3_sample.data

import com.an.paginglib3_sample.BuildConfig
import com.an.paginglib3_sample.api.NewsApiService
import com.an.paginglib3_sample.model.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchNews(
        query: String,
        nextPage: Long
    ): List<Article>? = apiService.fetchFeed(
        q = query,
        apiKey = BuildConfig.api_key,
        pageSize = 20,
        page = nextPage
    ).body()?.articles
}