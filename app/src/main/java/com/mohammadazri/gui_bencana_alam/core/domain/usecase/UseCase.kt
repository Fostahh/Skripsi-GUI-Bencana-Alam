package com.mohammadazri.gui_bencana_alam.core.domain.usecase

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.util.Resource

interface UseCase {
    fun getCurrentLocation(): LiveData<LatLng?>
    fun stopLocationUpdates()
    fun resumeLocationUpdates()
    suspend fun getDisasters(): Resource<List<Disaster>>
    suspend fun getDisastersByFilter(filter: String): Resource<List<Disaster>>
    suspend fun getDisasterById(id: String): Resource<Disaster>
}