package com.vobbla16.mesh.data.remote

import android.util.Log
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.util.AttributeKey
import kotlin.coroutines.cancellation.CancellationException

class InsertAuthKtorPluginConfig {
    var token: String? = null
}

val InsertAuthKtorPlugin =
    createClientPlugin("InsertAuthKtorPlugin", ::InsertAuthKtorPluginConfig) {
        val token = pluginConfig.token

        onRequest { request, _ ->
            if (!request.attributes.contains(InsertAuthAttrs.DontInsert)) {
                token?.let {
                    request.headers.apply {
                        append("Auth-Token", it)
                        append("Authorization", it)
                        append("X-mes-subsystem", "familyweb")
                    }
                }
                if (token == null) {
                    throw NoTokenException()
                }
            }
        }
    }

object InsertAuthAttrs {
    val DontInsert = AttributeKey<Boolean>("DontInsert")
}
class NoTokenException() : Exception("No token provided")