package com.an.paginglib3_sample.model

/**
 * Defines the api response from:
 * https://newsapi.org/v2/everything?q={query}&apiKey={apiKey}&pageSize=20&page=1
 */
data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String,
    val publishedAt: String
)
