package com.vobbla16.mesh.common


sealed class DataOrError<out T> {
    data class Success<T>(val data: T) : DataOrError<T>()
    data class Error(val e: Throwable) : DataOrError<Nothing>()
}
