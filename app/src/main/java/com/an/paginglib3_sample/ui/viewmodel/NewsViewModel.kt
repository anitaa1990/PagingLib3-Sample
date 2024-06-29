package com.an.paginglib3_sample.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.data.NewsRepository
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.ui.component.search.SearchWidgetState
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
    private val repository: NewsRepository
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _updateUi = MutableStateFlow(false)

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
        _updateUi.value = newValue.isEmpty()
    }

    fun onSearchClosed(items: LazyPagingItems<Article>) {
        updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
        if (_updateUi.value) refresh(items)
    }

    fun getNews(): Flow<PagingData<Article>> {
        updateRefresh(true)

        val query = _searchTextState.value.ifEmpty { "movies" }
        val articles = repository.getNews(query).cachedIn(viewModelScope)

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