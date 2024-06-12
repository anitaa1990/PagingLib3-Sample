package com.an.paginglib3_sample.model

data class NewsApiResponse(
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)