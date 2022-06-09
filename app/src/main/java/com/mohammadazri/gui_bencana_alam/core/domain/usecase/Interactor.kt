package com.mohammadazri.gui_bencana_alam.core.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: IRepository) : UseCase {
    override fun savePermissionsStatus(status: Boolean) = repository.savePermissionsStatus(status)
    override fun loadPermissionStatus(): Boolean = repository.loadPermissionStatus()
    override fun getCurrentLocation(): LiveData<LatLng?> = repository.getCurrentLocation()
    override fun stopLocationUpdates() = repository.stopLocationUpdates()
    override fun resumeLocationUpdates() = repository.resumeLocationUpdates()
    override fun getDisasters(): Flow<Resource<List<Disaster>>> {
        Log.d("ViewModel", "Interactor")
        return repository.getDisasters()
    }
    override fun getDisastersByFilter(filter: String): Flow<Resource<List<Disaster>>> {
        Log.d("ViewModelFilter", "Interactor")
        return repository.getDisastersByFilter(filter)
    }
}