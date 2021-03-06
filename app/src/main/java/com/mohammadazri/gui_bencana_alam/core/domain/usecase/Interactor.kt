package com.mohammadazri.gui_bencana_alam.core.domain.usecase

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: IRepository) : UseCase {
    override fun getCurrentLocation(): LiveData<LatLng?> = repository.getCurrentLocation()
    override fun stopLocationUpdates() = repository.stopLocationUpdates()
    override fun resumeLocationUpdates() = repository.resumeLocationUpdates()
    override suspend fun getDisasters(): Resource<List<Disaster>> = repository.getDisasters()
    override suspend fun getDisastersByFilter(filter: String): Resource<List<Disaster>> = repository.getDisastersByFilter(filter)
    override suspend fun getDisasterById(id: String): Resource<Disaster> = repository.getDisasterById(id)
}