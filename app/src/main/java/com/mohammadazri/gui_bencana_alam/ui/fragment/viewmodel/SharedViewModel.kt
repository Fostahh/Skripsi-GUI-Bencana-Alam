package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import com.mohammadazri.gui_bencana_alam.core.util.DataMapper
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
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

    private var tesDisasters = ArrayList<Disaster>()
    private var _tesListDisaster = MutableLiveData<List<Disaster>>()
    val tesListDisaster: LiveData<List<Disaster>> = _tesListDisaster

//    fun getDisasters(): LiveData<Resource<List<Disaster>>> {
//        Log.d("ViewModel", "Tess")
//        return useCase.getDisasters().asLiveData()
//    }

    fun getDisasters(): LiveData<Resource<List<Disaster>>> {
        Log.d("ViewModel", "ViewModel")
        return useCase.getDisasters().asLiveData()
    }

    fun getDisastersByFilter(filter: String): LiveData<Resource<List<Disaster>>> {
        Log.d("ViewModelFilter", "ViewModel")
        return useCase.getDisastersByFilter(filter).asLiveData()}

//    fun getDisasters(): LiveData<Resource<List<Disaster>>> = useCase.getDisasters()
//
//    fun getDisastersByFilter(filter: String? = "gempa"): LiveData<Resource<List<Disaster>>> =
//        useCase.getDisastersByFilter(filter)

//    fun addDisasters(listDisaster: List<Disaster>) {
////        tesDisasters.clear()
//        listDisaster.map {
//            tesDisasters.add(it)
//        }
////        Log.d("TesViewModel", "Masuak $tesDisasters")
//        _tesListDisaster.postValue(tesDisasters)
//    }
//
//    fun filterDisastersGempa() {
//        tesDisasters.map {
//            if (it.type == "gempa") _tesListDisaster.postValue(listOf(it))
//        }
//    }
//
//    fun filterDisastersBanjir() {
//        tesDisasters.map {
//            if (it.type == "banjir") _tesListDisaster.postValue(listOf(it))
//        }
//    }
//
//    fun noFilterDisasters() =
//        _tesListDisaster.postValue(tesDisasters)

}