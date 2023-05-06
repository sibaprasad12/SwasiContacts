package com.swasi.utility.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


object UserPreference {
    val APP_INSTALLED = booleanPreferencesKey("app_installed")
    val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
    val USER_LAST_NAME = stringPreferencesKey("user_last_name")
    val USER_BIRTH_DAY = longPreferencesKey("user_birth_day")
}