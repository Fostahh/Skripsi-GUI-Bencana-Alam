package com.mohammadazri.gui_bencana_alam.core.data

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.local.LocalDataSource
import com.mohammadazri.gui_bencana_alam.core.domain.IRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val fusedLocationClient: FusedLocationProviderClient,
) : IRepository {
    private val latLng = MutableLiveData<LatLng?>()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)

            Log.d("MapsFragmentTestingory", "${latLng.value}")
            latLng.postValue(LatLng(
                result.lastLocation.latitude,
                result.lastLocation.longitude
            ))
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000L
        fastestInterval = 5000L
    }

    override fun savePermissionsStatus(status: Boolean) =
        localDataSource.savePermissionsStatus(status)

    override fun loadPermissionStatus(): Boolean =
        localDataSource.loadPermissionStatus()

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): LiveData<LatLng?> {
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let { result ->
                    latLng.postValue(LatLng(
                        result.latitude,
                        result.longitude
                    ))
                } ?: run {
                    latLng.postValue(null)
                }
            } else {
                latLng.postValue(null)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())

        Log.d("MapsFragmentStart", "${latLng.value}")

        return latLng
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("MapsFragmentPause", "${latLng.value}")
    }

    @SuppressLint("MissingPermission")
    override fun resumeLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
        Log.d("MapsFragmentResume", "${latLng.value}")
    }
}