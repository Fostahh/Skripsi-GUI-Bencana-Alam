package com.mohammadazri.gui_bencana_alam.core.data.source.local

interface ILocalDataSource {
    fun savePermissionsStatus(status: Boolean)
    fun loadPermissionStatus(): Boolean
}