package com.vobbla16.mesh.common


import kotlin.reflect.KProperty1
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions

fun <T : Any, V> T.copyDynamic(
    property: KProperty1<T, V>,
    value: V,
): T {
    val copyFn = this::class.memberFunctions.single { it.name == "copy" }
    val instanceParam = copyFn.instanceParameter!!
    val propertyParam = copyFn.parameters.single { it.name == property.name }

    @Suppress("UNCHECKED_CAST")
    return copyFn.callBy(mapOf(
        instanceParam to this,
        propertyParam to value)) as T
}