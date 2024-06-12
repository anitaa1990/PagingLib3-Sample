package com.an.paginglib3_sample.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * By using a sealed interface, we ensure that all potential data states are explicitly handled
 * in the code. This leads to safer, more predictable behavior and eliminates the risk of
 * unhandled states.
 */
sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

/**
 * Transforms a Flow<T> into a Flow<Result<T>>, thereby automating the handling of loading,
 * success, and error states.
 */
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        // Each element emitted by the original Flow is wrapped in a Result.Success,
        // effectively capturing successful data emissions
        .map<T, Result<T>> { Result.Success(it) }
        // This emits a Result.Loading before any data is emitted, signaling the start of a
        // data-fetching operation. This is particularly useful for triggering loading indicators
        // in the UI
        .onStart { emit(Result.Loading) }
        // Errors are caught and wrapped in a Result.Error, providing a standardized way to
        // handle exceptions
        .catch { emit(Result.Error(it)) }
}