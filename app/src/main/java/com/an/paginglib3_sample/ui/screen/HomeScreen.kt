package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState

import androidx.paging.compose.collectAsLazyPagingItems
import com.an.paginglib3_sample.ui.component.BookPager
import com.an.paginglib3_sample.ui.component.BookPagerOrientation
import com.an.paginglib3_sample.ui.component.ErrorScreen
import com.an.paginglib3_sample.ui.component.LoadingItem
import com.an.paginglib3_sample.ui.component.NewsItem
import com.an.paginglib3_sample.ui.component.SearchMenu
import com.an.paginglib3_sample.ui.component.SearchWidgetState
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onItemClicked: (url: String) -> Unit,
    onShareButtonClicked: (url: String) -> Unit
) {
    val news = viewModel.news.collectAsLazyPagingItems()
    val inputText = viewModel.inputText.collectAsState()
    val searchWidgetState by viewModel.searchWidgetState

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Different load states – Loading, Empty State, Pager list state
        val loadState = news.loadState
        when (loadState.refresh) {
            is LoadState.Loading -> {
                LoadingItem()
            }
            is LoadState.Error -> {
                val error = (loadState.refresh as LoadState.Error).error
                ErrorScreen(errorMessage = error.message ?: error.toString()) {
                    news.refresh()
                }
            }
            else -> {
                // News List
                val pagerState = rememberPagerState { news.itemCount }
                BookPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    orientation = BookPagerOrientation.Vertical,
                ) { page ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
                    ) {
                        news[page]?.let {
                            NewsItem(
                                modifier = Modifier.align(Alignment.Center),
                                article = it,
                                onItemClicked = onItemClicked,
                                onShareButtonClicked = onShareButtonClicked
                            )
                        }
                    }
                }

                // Search menu
                SearchMenu(
                    inputText = inputText.value,
                    searchWidgetState = searchWidgetState,
                    onTextChange = { viewModel.updateSearchInput(it) },
                    onCloseClicked = {
                        viewModel.onSearchClosed()
                        news.refresh()
                    },
                    onSearchClicked = { news.refresh() },
                    onSearchIconClicked = {
                        viewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                    }
                )
            }
        }
    }
}
