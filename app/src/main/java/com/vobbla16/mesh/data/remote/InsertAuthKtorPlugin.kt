package com.vobbla16.mesh.data.remote

import io.ktor.client.plugins.api.createClientPlugin

class InsertAuthKtorPluginConfig {
    var token: String? = null
}

val InsertAuthKtorPlugin =
    createClientPlugin("InsertAuthKtorPlugin", ::InsertAuthKtorPluginConfig) {
        val token = pluginConfig.token

        onRequest { request, _ ->
            token?.let {
                request.headers.apply {
                    append("Auth-Token", it)
                    append("Authorization", it)
                    append("X-mes-subsystem", "familyweb")
                }
            }
        }
    }