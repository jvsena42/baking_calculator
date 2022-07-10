package com.bulletapps.candypricer.presentation.util

import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext


suspend fun <T> safeRequest(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    try {
        Resource.Success(block())
    } catch (e: Exception) {
        Resource.Error(UiText.DynamicString(e.message.orEmpty()))
    }
}