package com.an.paginglib3_sample.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.data.NewsDataSource
import com.an.paginglib3_sample.data.NewsRepository
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.ui.component.search.SearchWidgetState
import com.an.paginglib3_sample.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    networkStatus: NetworkStatus
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getNews(query: String): Flow<PagingData<Article>> {
        updateRefresh(true)

        val articles = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as `prefetchDistance` or `pageSize`.
            PagingConfig(
                // defines the number of items loaded at once from the PagingSource
                pageSize = 2
            )
        ) { NewsDataSource(repository, query) }
            // converts the data into a Flow (or LiveData)
            .flow
            // ensures that upon configuration change, the new Activity will receive the
            // existing data immediately rather than fetching it from scratch
            .cachedIn(viewModelScope)

        updateRefresh(false)
        return articles
    }

    fun refresh(items: LazyPagingItems<Article>) {
        updateRefresh(true)
        items.refresh()
        updateRefresh(false)
    }

    private fun updateRefresh(value: Boolean) {
        viewModelScope.launch {
            // Workaround to delay refresh by 1s in order for PullToRefresh to work correctly
            if (!value) { delay(1000) }
            _isRefreshing.value = value
        }
    }
}