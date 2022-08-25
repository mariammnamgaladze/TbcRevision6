package com.example.tbcrevision6.data
import android.app.Application
import com.example.tbcrevision6.App
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

object DataStoreHandler {

    private val Application.store by preferencesDataStore(
        name = "test_name"
    )

    fun getPreferences(): Flow<Preferences> {
        return App.appContext.store.data
    }

    suspend fun save(key: String, value: String) {
        App.appContext.store.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun clear() {
        App.appContext.store.edit {
            it.clear()
        }
    }

    suspend fun remove(key: String) {
        App.appContext.store.edit {
            it.remove(stringPreferencesKey(key))
        }
    }

}
