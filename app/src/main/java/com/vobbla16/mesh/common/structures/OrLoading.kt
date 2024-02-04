package com.vobbla16.mesh.common.structures

sealed class OrLoading<out T> {
    data class Data<T>(val res: T): OrLoading<T>()
    data object Loading: OrLoading<Nothing>()
}