package com.vobbla16.mesh.data.settingsStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vobbla16.mesh.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val TOKEN_KEY = stringPreferencesKey("TOKEN")
    }

    override val token: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    override suspend fun setToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    override suspend fun removeToken() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }
}