package com.activecampaign.contacs.presentation.ext

internal suspend inline fun <T> (suspend () -> Result<T>).retryUntilSuccess(
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
