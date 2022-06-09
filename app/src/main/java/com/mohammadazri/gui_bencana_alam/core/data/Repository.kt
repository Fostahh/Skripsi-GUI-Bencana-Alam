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
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.RemoteDataSource
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.util.DataMapper
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import com.mohammadazri.gui_bencana_alam.util.Constant.FUSED_LOCATION_FASTEST_INTERVAL
import com.mohammadazri.gui_bencana_alam.util.Constant.FUSED_LOCATION_INTERVAL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val remoteDataSource: RemoteDataSource,
) : IRepository {

    private val latLng = MutableLiveData<LatLng?>()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)

            latLng.postValue(
                LatLng(
                    result.lastLocation.latitude,
                    result.lastLocation.longitude
                )
            )

            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = FUSED_LOCATION_INTERVAL
        fastestInterval = FUSED_LOCATION_FASTEST_INTERVAL
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
                    latLng.postValue(
                        LatLng(
                            result.latitude,
                            result.longitude
                        )
                    )
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
            Looper.getMainLooper()
        )

        return latLng
    }

    override fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    override fun resumeLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun getDisasters(): Flow<Resource<List<Disaster>>> = flow {
        remoteDataSource.getDisasters().collect {
            Log.d("ViewModel", "Repository")
            when (it) {
                is ApiResponse.Success -> emit(
                    Resource.Success(
                        DataMapper.disastersResponseToDisasterDomain(
                            it.data
                        )
                    )
                )
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Loading -> emit(Resource.Loading())
                else -> emit(Resource.Error("Terjadi error"))
            }
        }
    }


    override fun getDisastersByFilter(filter: String): Flow<Resource<List<Disaster>>> = flow {
        remoteDataSource.getDisastersByFilter(filter).collect {
            Log.d("ViewModelFilter", "Repository")
            when (it) {
                is ApiResponse.Success -> emit(
                    Resource.Success(
                        DataMapper.disastersResponseToDisasterDomain(
                            it.data
                        )
                    )
                )
                is ApiResponse.Error -> emit(Resource.Error(it.errorMessage))
                is ApiResponse.Loading -> emit(Resource.Loading())
                else -> emit(Resource.Error("Terjadi error"))
            }
        }
    }

}