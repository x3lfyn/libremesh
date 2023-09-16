package com.vobbla16.mesh.data.remote

import com.vobbla16.mesh.domain.use_case.GetTokenUseCase
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.util.AttributeKey

class InsertAuthKtorPluginConfig {
    var getTokenUseCase: GetTokenUseCase? = null
}

val InsertAuthKtorPlugin =
    createClientPlugin("InsertAuthKtorPlugin", ::InsertAuthKtorPluginConfig) {
        onRequest { request, _ ->
            val token = this@createClientPlugin.pluginConfig.getTokenUseCase?.invoke()

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
class NoTokenException : Exception("No token provided")