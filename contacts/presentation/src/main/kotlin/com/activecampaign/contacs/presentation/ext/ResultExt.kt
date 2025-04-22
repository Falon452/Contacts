package com.activecampaign.contacs.presentation.ext

internal typealias SuspendedResultBlock<T> = suspend () -> Result<T>

internal suspend inline fun <T> SuspendedResultBlock<T>.executeWithRetry(
    maxAttempts: Int,
    noinline onRetry: suspend (attempt: Int, exception: Throwable) -> Unit,
): Result<T> {
    repeat(maxAttempts - 1) { attempt ->
        val result = this()
        if (result.isSuccess) {
            return result
        }
        result.exceptionOrNull()?.let { onRetry(attempt + 1, it) }
    }
    return this()
}
