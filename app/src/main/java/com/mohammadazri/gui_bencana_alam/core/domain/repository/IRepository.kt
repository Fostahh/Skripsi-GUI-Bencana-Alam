package com.mohammadazri.gui_bencana_alam.core.domain.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

interface IRepository {
    fun savePermissionsStatus(status: Boolean)
    fun loadPermissionStatus(): Boolean
    fun getCurrentLocation(): LiveData<LatLng?>
    fun stopLocationUpdates()
    fun resumeLocationUpdates()

    fun getDisasters(): LiveData<List<Disaster>>
}