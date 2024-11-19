package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.ui.component.ErrorScreen
import com.an.paginglib3_sample.ui.component.LoadingItem
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    items: LazyPagingItems<Article>
) {
    PagingLib3SampleTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Different load states – Loading, Empty State, Pager list state
            val loadState = items.loadState.mediator
            when (loadState?.refresh) {
                is LoadState.Loading -> {
                    LoadingItem()
                }
                is LoadState.Error -> {
                    val error = (loadState.refresh as LoadState.Error).error
                    ErrorScreen(errorMessage = error.message ?: error.toString()) {
                        items.refresh()
                    }
                }
                else -> {
                    // News List
                    // TODO:
                }
            }
        }
    }
}
