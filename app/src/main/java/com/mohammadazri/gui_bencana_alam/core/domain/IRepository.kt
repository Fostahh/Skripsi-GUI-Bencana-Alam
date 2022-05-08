package com.mohammadazri.gui_bencana_alam.core.domain

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng

interface IRepository {
    fun savePermissionsStatus(status: Boolean)
    fun loadPermissionStatus(): Boolean
    fun getCurrentLocation(): LiveData<LatLng?>
    fun stopLocationUpdates()
    fun resumeLocationUpdates()
}