package com.an.paginglib3_sample.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.ui.component.pulltorefresh.PullRefreshIndicator
import com.an.paginglib3_sample.ui.component.pulltorefresh.pullRefresh
import com.an.paginglib3_sample.ui.component.pulltorefresh.rememberPullRefreshState

@Composable
fun <T : Any> PullToRefreshLazyColumn(
    items: LazyPagingItems<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: State<Boolean>,
    onRefresh: () -> Unit,
    onError: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val state = rememberPullRefreshState(isRefreshing.value, onRefresh)
    Box(modifier = modifier.pullRefresh(state = state)) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isRefreshing.value) {
                    items(items.itemCount) { index ->
                        items[index]?.let { content(it) }
                    }

                    items.apply {
                        when {
                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                onError()
                            }
                            loadState.refresh is LoadState.Loading -> {

                            }
                            loadState.append is LoadState.Loading -> {

                            }
                        }
                    }
                }
            }
        PullRefreshIndicator(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            refreshing = isRefreshing.value,
            state = state,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

//    val pullToRefreshState = rememberPullToRefreshState()
//
//        PullToRefreshBox(
//            isRefreshing = isRefreshing.value,
//            onRefresh = onRefresh,
//            state = pullToRefreshState,
//            modifier = modifier,
//            contentAlignment = Alignment.TopCenter
//        ) {
//            LazyColumn(
//                state = lazyListState,
//                contentPadding = PaddingValues(8.dp),
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                if (!isRefreshing.value) {
//                    items(items.itemCount) { index ->
//                        items[index]?.let { content(it) }
//                    }
//
//                    items.apply {
//                        when {
//                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
//                                onError()
//                            }
//                            loadState.refresh is LoadState.Loading -> {
//                                pullToRefreshState.isAnimating
//                            }
//                            loadState.append is LoadState.Loading -> {
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
}