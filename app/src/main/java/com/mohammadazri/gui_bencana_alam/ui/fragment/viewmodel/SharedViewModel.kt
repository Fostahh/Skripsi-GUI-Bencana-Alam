package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SharedViewModel @Inject constructor(private val useCase: UseCase) : ViewModel(),
    CoroutineScope {
    //    fun savePermissionsStatus(status: Boolean) = useCase.savePermissionsStatus(status)
//    fun loadPermissionStatus(): Boolean = useCase.loadPermissionStatus()
//    fun getLocationBasedOnGPS(): LiveData<>
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

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
                        i
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("MapsFragmentTesting", e.localizedMessage ?: "Error")
        }

        return addressText
    }

    fun stopLocationUpdates() = useCase.stopLocationUpdates()
    fun resumeLocationUpdates() = useCase.resumeLocationUpdates()


    /*
        INI TESTING FILTER FEATURE
    */

    var disasterLiveData: MutableLiveData<List<Disaster>> = MutableLiveData()
    var filteredLiveData: MutableLiveData<List<Disaster>> = MutableLiveData()

    fun getDisasters() {
        launch {
            val disasters = withContext(Dispatchers.IO) {
                useCase.getDisasters()
            }
            when (disasters) {
                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    disasters.data?.let {
                        disasterLiveData.value = it
                    }

                }
            }
        }
    }

    fun getDisastersByFilter(filter: String) {
        launch {
            val disasters = withContext(Dispatchers.IO) {
                useCase.getDisastersByFilter(filter)
            }
            when (disasters) {
                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    disasters.data?.let {
                        filteredLiveData.value = it
                    }

                }
            }
        }
    }

}