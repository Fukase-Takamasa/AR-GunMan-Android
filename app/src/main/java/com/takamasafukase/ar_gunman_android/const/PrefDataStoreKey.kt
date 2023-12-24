package com.takamasafukase.ar_gunman_android.const

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

class PrefDataStoreKey {
    companion object {
        val isTutorialAlreadySeen = booleanPreferencesKey(name = "isTutorialAlreadySeen")
    }
}