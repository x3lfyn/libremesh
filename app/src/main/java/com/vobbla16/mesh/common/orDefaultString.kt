package com.vobbla16.mesh.common

fun String?.orDefault(): String = when(this) {
    null -> Constants.DEFAULT_STRING
    else -> this
}