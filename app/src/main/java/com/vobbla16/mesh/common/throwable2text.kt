package com.vobbla16.mesh.common

fun Throwable.toText(): String = this.localizedMessage ?: Constants.DEFAULT_ERROR_MESSAGE