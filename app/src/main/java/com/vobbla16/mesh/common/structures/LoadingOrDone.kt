package com.vobbla16.mesh.common.structures

sealed class LoadingOrDone {
    data object Loading: LoadingOrDone()
    data object Done: LoadingOrDone()
}