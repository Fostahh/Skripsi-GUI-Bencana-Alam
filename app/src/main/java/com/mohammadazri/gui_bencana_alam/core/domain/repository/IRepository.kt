package com.mohammadazri.gui_bencana_alam.core.domain.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.util.Resource

interface IRepository {
    fun savePermissionsStatus(status: Boolean)
    fun loadPermissionStatus(): Boolean
    fun getCurrentLocation(): LiveData<LatLng?>
    fun stopLocationUpdates()
    fun resumeLocationUpdates()
    fun getDisasters(): LiveData<Resource<List<Disaster>>>
    fun getDisastersByFilter(filter: String?): LiveData<Resource<List<Disaster>>>
}