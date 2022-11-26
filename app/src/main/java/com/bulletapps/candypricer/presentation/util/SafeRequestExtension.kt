package com.bulletapps.candypricer.presentation.util

import android.util.Log
import com.bulletapps.candypricer.config.Resource
import com.bulletapps.candypricer.config.UiText
import com.bulletapps.candypricer.domain.model.ErrorModel
import com.google.gson.Gson
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
        getErrorMessage(e)
    }
}

private fun <T> getErrorMessage(e: Exception): Resource.Error<T> {
    var errorModel = ErrorModel(userMessage = "Ocorreu um erro inesperado, tente novamente mais tarde")
    try {
        errorModel = Gson().fromJson(e.message, ErrorModel::class.java)
    } catch (e2: Exception) {
        errorModel.devMessage = e.message
    }
    Log.d("REQUEST_ERROR", "safeRequest: ${errorModel.devMessage}")
    return Resource.Error(UiText.DynamicString(errorModel.userMessage.orEmpty()))
}

suspend fun <T> safeRequest2(
    dispatcher: CoroutineDispatcher,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    try {
        Result.success(block())
    } catch (e: Exception) {
        getErrorMessage2(e)
    }
}

private fun <T> getErrorMessage2(e: Exception): Result<T> {
    var errorModel = ErrorModel(userMessage = "Ocorreu um erro inesperado, tente novamente mais tarde")
    try {
        errorModel = Gson().fromJson(e.message, ErrorModel::class.java)
    } catch (e2: Exception) {
        errorModel.devMessage = e.message
    }
    Log.d("REQUEST_ERROR", "safeRequest: ${errorModel.devMessage}")
    return Result.failure(e)
}
