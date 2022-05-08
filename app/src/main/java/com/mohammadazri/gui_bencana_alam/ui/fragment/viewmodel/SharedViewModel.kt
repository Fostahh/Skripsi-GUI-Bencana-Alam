package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.Repository
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FragmentViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
//    fun savePermissionsStatus(status: Boolean) = useCase.savePermissionsStatus(status)
//    fun loadPermissionStatus(): Boolean = useCase.loadPermissionStatus()
//    fun getLocationBasedOnGPS(): LiveData<>

    fun getCurrentLocation(): LiveData<LatLng?> = useCase.getCurrentLocation()

    fun getAddress(latLng: LatLng, context: Context): String {
        val geocoder = Geocoder(context)
        val address: Address?
        var addressText = ""

        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                address = addresses[0]
                for (i in 0..address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                        i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsFragmentTesting", e.localizedMessage ?: "Error")
        }

        return addressText
    }

    fun stopLocationUpdates() = useCase.stopLocationUpdates()
}