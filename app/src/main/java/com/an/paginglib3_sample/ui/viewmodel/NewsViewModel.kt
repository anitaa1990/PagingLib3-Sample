package com.an.paginglib3_sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.an.paginglib3_sample.data.NewsDataSource
import com.an.paginglib3_sample.data.NewsRepository
import com.an.paginglib3_sample.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    fun getNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
        ) { NewsDataSource(repository, query) }
            .flow
            .cachedIn(viewModelScope)
    }
}