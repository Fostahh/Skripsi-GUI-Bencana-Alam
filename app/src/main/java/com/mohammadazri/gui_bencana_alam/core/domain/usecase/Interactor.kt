package com.mohammadazri.gui_bencana_alam.core.domain.usecase

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.IRepository
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: IRepository) : UseCase {
    override fun savePermissionsStatus(status: Boolean) = repository.savePermissionsStatus(status)

    override fun loadPermissionStatus(): Boolean = repository.loadPermissionStatus()

    override fun getCurrentLocation(): LiveData<LatLng?> = repository.getCurrentLocation()
    override fun stopLocationUpdates() = repository.stopLocationUpdates()
}