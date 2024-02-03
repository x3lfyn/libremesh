package com.vobbla16.mesh.common

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
fun <T, R> Flow<OrLoading<Resource<T>>>.mapIfOk(
    transform: (T) -> R
): Flow<OrLoading<Resource<R>>> {
    return this.map {
        when (it) {
            is OrLoading.Data -> {
                when (it.res) {
                    is Resource.Ok -> OrLoading.Data(Resource.Ok(transform(it.res.data)))
                    else -> it as OrLoading<Resource<R>>
                }
            }

            else -> it as OrLoading<Resource<R>>
        }
    }
}