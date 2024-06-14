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
import com.an.paginglib3_sample.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    networkStatus: NetworkStatus
) : ViewModel() {
    private val _state = networkStatus.isConnected.distinctUntilChanged()
    val state: StateFlow<Boolean> = _state.stateIn(
        initialValue = networkStatus.isConnected(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

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