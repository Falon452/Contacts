package com.activecampaign.contacs.presentation.ext

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExecuteWithRetryTest {

    @Test
    fun `WHEN executeWithRetry is called with failure, THEN it retries maxAttempts times before success`() = runBlocking {
        var callCount = 0

        val suspendedResultBlock: suspend () -> Result<String> = {
            callCount++
            if (callCount < 3) {
                failure(Exception("Failed attempt $callCount"))
            } else {
                success("Success")
            }
        }

        val retryAttempts = mutableListOf<Int>()

        val onRetry: suspend (Int, Throwable) -> Unit = { attempt, exception ->
            retryAttempts += attempt
        }

        val result = suspendedResultBlock.executeWithRetry(maxAttempts = 3, onRetry = onRetry)

        assertTrue(result.isSuccess)
        assertEquals("Success", result.getOrNull())
        assertEquals(listOf(1, 2), retryAttempts)
        assertEquals(3, callCount)
    }


    @Test
    fun `WHEN executeWithRetry is called with failure for all attempts, THEN it returns failure after maxAttempts`() = runBlocking {
        var callCount = 0

        val suspendedResultBlock: suspend () -> Result<String> = {
            callCount++
            failure(Exception("Failed attempt $callCount"))
        }

        val retryAttempts = mutableListOf<Int>()

        val onRetry: suspend (Int, Throwable) -> Unit = { attempt, exception ->
            retryAttempts += attempt
        }

        val result = suspendedResultBlock.executeWithRetry(maxAttempts = 3, onRetry = onRetry)

        assertTrue(result.isFailure)
        assertEquals("Failed attempt 3", result.exceptionOrNull()?.message)
        assertEquals(listOf(1, 2), retryAttempts)
        assertEquals(3, callCount)
    }

}
