package com.an.paginglib3_sample.api

import com.an.paginglib3_sample.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {
    /**
     * We would be using the below url:
     * https://newsapi.org/v2/everything?q={query}&apiKey={api}&pageSize={pageSize}&page={page}
     * It has four query parameters: query, apiKey, page & pageSize
     */
    @GET("/v2/everything")
    suspend fun fetchFeed(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Long,
        @Query("pageSize") pageSize: Int
    ): Response<NewsApiResponse>
}