package com.an.paginglib3_sample.ui.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackBarAppState(
    val snackBarHostState: SnackbarHostState,
    val snackBarScope: CoroutineScope
) {
    fun showSnackBar(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        snackBarScope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberSnackBarAppState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackBarScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackBarHostState, snackBarScope) {
    SnackBarAppState(
        snackBarHostState = snackBarHostState,
        snackBarScope = snackBarScope
    )
}
