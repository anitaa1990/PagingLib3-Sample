package com.an.paginglib3_sample.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.an.paginglib3_sample.api.NewsApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    fun getNews(query: String) = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as `prefetchDistance` or `pageSize`.
        PagingConfig(
            // defines the number of items loaded at once from the PagingSource
            pageSize = 2
        ),
        pagingSourceFactory = {
            NewsDataSource(apiService, query)
        }
    )
        // converts the data into a Flow (or LiveData)
        .flow
}