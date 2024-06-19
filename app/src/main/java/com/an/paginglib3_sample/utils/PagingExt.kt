package com.an.paginglib3_sample.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyPagingItems<T>.isEmpty(): Boolean {
    return (loadState.refresh is LoadState.NotLoading && itemCount == 0)
}