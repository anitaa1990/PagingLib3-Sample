package com.an.paginglib3_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.an.paginglib3_sample.ui.screen.HomeScreen
import com.an.paginglib3_sample.ui.theme.PagingLib3SampleTheme
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel
import com.an.paginglib3_sample.util.openUrl
import com.an.paginglib3_sample.util.share
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            PagingLib3SampleTheme {
                val viewModel: NewsViewModel = hiltViewModel()
                val news = viewModel.news.collectAsLazyPagingItems()
                val context = LocalContext.current

                HomeScreen(
                    news = news,
                    onItemClicked = { context.openUrl(it) },
                    onShareButtonClicked = { context.share(it) }
                )
            }
        }
    }
}