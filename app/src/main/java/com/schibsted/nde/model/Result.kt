package com.schibsted.nde.model

sealed interface Result<T> {
    class Success<T>(val data: T) : Result<T>
    class Loading<T> : Result<T>
    class Error<T>(val e: Throwable) : Result<T>
}
