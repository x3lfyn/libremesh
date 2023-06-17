package com.vobbla16.mesh.common

sealed class DataOrErrorOrNotLoggedIn<out T> {
    data class Ok<T>(val data: T) : DataOrErrorOrNotLoggedIn<T>()
    data class Err(val msg: String) : DataOrErrorOrNotLoggedIn<Nothing>()
    object NotLoggedIn : DataOrErrorOrNotLoggedIn<Nothing>()
}