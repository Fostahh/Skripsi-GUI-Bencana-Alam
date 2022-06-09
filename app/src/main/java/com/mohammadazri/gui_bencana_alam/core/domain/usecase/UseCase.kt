package com.mohammadazri.gui_bencana_alam.core.domain.usecase

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun savePermissionsStatus(status: Boolean)
    fun loadPermissionStatus(): Boolean
    fun getCurrentLocation(): LiveData<LatLng?>
    fun stopLocationUpdates()
    fun resumeLocationUpdates()
    fun getDisasters(): Flow<Resource<List<Disaster>>>
    fun getDisastersByFilter(filter: String): Flow<Resource<List<Disaster>>>
}