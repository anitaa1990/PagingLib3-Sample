package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.an.paginglib3_sample.model.Article
import com.an.paginglib3_sample.ui.component.SnackBarAppState
import com.an.paginglib3_sample.ui.component.rememberSnackBarAppState
import com.an.paginglib3_sample.ui.component.search.DefaultAppBar
import com.an.paginglib3_sample.ui.component.search.SearchAppBar
import com.an.paginglib3_sample.ui.component.search.SearchWidgetState
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    items: LazyPagingItems<Article>
) {
    PagingLib3SampleTheme {
        val appState: SnackBarAppState = rememberSnackBarAppState()

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = appState.snackBarHostState) },
            modifier = Modifier.fillMaxSize(),
            topBar = {
                val searchWidgetState by viewModel.searchWidgetState
                val searchTextState by viewModel.searchTextState

                MainTopAppBar(
                    searchWidgetState = searchWidgetState,
                    searchTextState = searchTextState,
                    onTextChange = {
                        viewModel.updateSearchTextState(newValue = it)
                    },
                    onCloseClicked = {
                        viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = { },
                    onSearchTriggered = {
                        viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                    }
                )
            }
        ) { innerPadding ->
            // Update the news list
            val isRefreshing = viewModel.isRefreshing.collectAsStateWithLifecycle(
                lifecycleOwner = LocalLifecycleOwner.current
            )
            NewsList(
                modifier = Modifier.padding(innerPadding),
                items = items,
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refresh(items) },
                snackBarAppState = appState
            )
        }
    }
}

@Composable
fun MainTopAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            DefaultAppBar(
                onSearchClicked = onSearchTriggered
            )
        }
        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}
