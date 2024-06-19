package com.an.paginglib3_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.an.paginglib3_sample.ui.screen.HomeScreen
import com.an.paginglib3_sample.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NewsViewModel = hiltViewModel()
            val lazyNewsItems = viewModel.getNews("movies").collectAsLazyPagingItems()

            HomeScreen(
                viewModel = viewModel,
                items = lazyNewsItems
            )
        }
    }
}