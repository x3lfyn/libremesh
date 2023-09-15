package com.vobbla16.mesh.common

sealed class OrLoading<out T> {
    data class Data<T>(val res: T): OrLoading<T>()
    object Loading: OrLoading<Nothing>()
}