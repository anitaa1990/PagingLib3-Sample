package com.an.paginglib3_sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.an.paginglib3_sample.data.NewsDataSource
import com.an.paginglib3_sample.data.NewsRepository
import com.an.paginglib3_sample.data.NewsRepository.Companion.PAGE_SIZE
import com.an.paginglib3_sample.data.NewsRepository.Companion.PREFETCH_DISTANCE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _inputText: MutableStateFlow<String> = MutableStateFlow("movies")
    val inputText: StateFlow<String> = _inputText

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val news = inputText
        .filter { it.isNotEmpty() }
        .debounce(300.milliseconds)
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE,
                    initialLoadSize = PAGE_SIZE,
                ),
                pagingSourceFactory = {
                    NewsDataSource(repository, query)
                }
            ).flow
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    // some articles come with removed content so adding this line
                    // to remove those articles from the list
                    pagingData.filter { it.title != "[Removed]" }
                }
        }
}