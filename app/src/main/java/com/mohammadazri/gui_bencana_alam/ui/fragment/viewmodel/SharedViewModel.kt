package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    fun getCurrentLocation(): LiveData<LatLng?> = useCase.getCurrentLocation()
    fun stopLocationUpdates() = useCase.stopLocationUpdates()
    fun resumeLocationUpdates() = useCase.resumeLocationUpdates()

    var listDisasterLiveData: MutableLiveData<List<Disaster>> = MutableLiveData()
    var filteredLiveData: MutableLiveData<List<Disaster>> = MutableLiveData()
    var disasterLiveData: MutableLiveData<Disaster> = MutableLiveData()
    var toastErrorLiveData: MutableLiveData<String?> = MutableLiveData()

//    fun getAddress(latLng: LatLng, context: Context): String {
//        val geocoder = Geocoder(context)
//        val address: Address?
//        var addressText = ""
//
//        try {
//            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            if (!addresses.isNullOrEmpty()) {
//                address = addresses[0]
//                for (i in 0..address.maxAddressLineIndex) {
//                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
//                        i
//                    )
//                }
//            }
//        } catch (e: IOException) {
//            Log.e("MapsFragmentTesting", e.localizedMessage ?: "Error")
//        }
//
//        return addressText
//    }

    fun getAddress(latLng: LatLng, context: Context): String {
        val geocoder = Geocoder(context)
        val address: Address?
        var fulladdress = ""
        val addresses: List<Address>? =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        addresses?.let {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                Log.d("Alamat Lokasi", "${address}")
                for (i in 0..address.maxAddressLineIndex) {
                    fulladdress += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                        i
                    )
                }
                fulladdress =
                    address.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex
            } else {
                fulladdress = "Location not found"
            }
        }

        return fulladdress
    }

    fun getDisasters() {
        viewModelScope.launch {
            val disasters = withContext(Dispatchers.IO) {
                useCase.getDisasters()
            }
            when (disasters) {
                is Resource.Error -> toastErrorLiveData.value = disasters.message
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    disasters.data?.let {
                        listDisasterLiveData.postValue(it)
                    }
                }
            }
        }
    }

    fun getDisastersByFilter(filter: String) {
        viewModelScope.launch {
            val disasters = withContext(Dispatchers.IO) {
                useCase.getDisastersByFilter(filter)
            }
            when (disasters) {
                is Resource.Error -> toastErrorLiveData.value = disasters.message
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    disasters.data?.let {
                        filteredLiveData.postValue(it)
                    }

                }
            }
        }
    }

    fun getDisasterById(id: String) {
        viewModelScope.launch {
            val disaster = withContext(Dispatchers.IO) {
                useCase.getDisasterById(id)
            }
            when (disaster) {
                is Resource.Error -> toastErrorLiveData.value = disaster.message
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    disaster.data?.let {
                        disasterLiveData.postValue(it)
                    }
                }
            }
        }
    }
}