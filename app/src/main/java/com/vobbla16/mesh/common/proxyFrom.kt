package com.vobbla16.mesh.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * Collects all events from [fromFlow] and emits them to current flow
 */
suspend fun<T> FlowCollector<T>.proxyFrom(fromFlow: Flow<T>) {
    fromFlow.onEach { emit(it) }.collect()
}