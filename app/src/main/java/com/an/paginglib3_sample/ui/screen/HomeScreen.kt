package com.an.paginglib3_sample.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.an.paginglib3_sample.R
import com.an.paginglib3_sample.ui.component.SnackBarAppState
import com.an.paginglib3_sample.ui.component.rememberSnackBarAppState
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel
) {
    PagingLib3SampleTheme {
        val appState: SnackBarAppState = rememberSnackBarAppState()

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = appState.snackBarHostState) },
            modifier = Modifier.fillMaxSize(),
            topBar = { MainTopAppBar() }
        ) { innerPadding ->

            // Update the news list
            NewsList(
                modifier = Modifier.padding(innerPadding),
                viewModel = viewModel,
                snackBarAppState = appState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}