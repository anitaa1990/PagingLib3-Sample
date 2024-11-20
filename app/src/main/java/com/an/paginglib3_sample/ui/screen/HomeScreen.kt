package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

@Composable
fun HomeScreen(
    news: LazyPagingItems<Article>,
    onItemClicked: (url: String) -> Unit,
    onShareButtonClicked: (url: String) -> Unit
) {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 55.dp, horizontal = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = {  }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
