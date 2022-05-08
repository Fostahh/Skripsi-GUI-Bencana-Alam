package com.mohammadazri.gui_bencana_alam.core.data.source.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val preferences: SharedPreferences) :
    ILocalDataSource {
    override fun savePermissionsStatus(status: Boolean) {
        preferences.edit().putBoolean(PERMISSION_STATUS, status).apply()
    }

    override fun loadPermissionStatus(): Boolean =
        preferences.getBoolean(PERMISSION_STATUS, false)


    companion object {
        const val PREFS_NAME = "user_pref"
        private const val PERMISSION_STATUS = "Permission Status"
        private const val PASAR = "pasar"
        private const val LOKASI = "lokasi"
        private const val TOKEN = "token"
    }
}