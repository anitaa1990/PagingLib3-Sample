package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.ui.component.BookPager
import com.an.paginglib3_sample.ui.component.BookPagerOrientation
import com.an.paginglib3_sample.ui.component.ErrorScreen
import com.an.paginglib3_sample.ui.component.LoadingItem
import com.an.paginglib3_sample.ui.component.NewsItem
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme

@Composable
fun HomeScreen(
    items: LazyPagingItems<Article>,
    onShareButtonClicked: (url: String) -> Unit
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
                    val pagerState = rememberPagerState { items.itemCount }
                    BookPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth(),
                        orientation = BookPagerOrientation.Vertical,
                    ) { page ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp)),
                        ) {
                            items[page]?.let {
                                NewsItem(
                                    modifier = Modifier.align(Alignment.Center),
                                    article = it,
                                    onShareButtonClicked = onShareButtonClicked
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
