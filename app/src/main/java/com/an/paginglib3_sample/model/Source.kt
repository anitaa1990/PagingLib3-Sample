package com.an.paginglib3_sample.model

/**
 * Defines the api response from:
 * https://newsapi.org/v2/everything?q={query}&apiKey={apiKey}&pageSize=20&page=1
 */
data class Source(
    val id: String,
    val name: String
)