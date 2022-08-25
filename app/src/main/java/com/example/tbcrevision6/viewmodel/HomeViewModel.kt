package com.example.tbcrevision6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tbcrevision6.data.DataStoreHandler

class HomeViewModel: ViewModel() {

    fun getPreferences() = DataStoreHandler.getPreferences()

    suspend fun clear() {
        DataStoreHandler.clear()
    }

    // With Remove
    suspend fun remove(key: String) {
        DataStoreHandler.remove(key)
    }

}