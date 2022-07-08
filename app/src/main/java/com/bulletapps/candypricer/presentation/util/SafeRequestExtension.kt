package com.bulletapps.candypricer.presentation.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import com.bulletapps.candypricer.domain.model.Result


suspend fun <T> safeRequest(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Failure(e)
    }
}