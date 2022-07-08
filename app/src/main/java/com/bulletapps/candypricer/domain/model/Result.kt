package com.bulletapps.candypricer.domain.model

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Failure(val e: Throwable): Result<Nothing>()
}