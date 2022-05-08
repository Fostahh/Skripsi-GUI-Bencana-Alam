package com.mohammadazri.gui_bencana_alam.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.databinding.FragmentMapsBinding
import com.mohammadazri.gui_bencana_alam.util.Constant
import com.vmadalin.easypermissions.EasyPermissions
import java.io.IOException


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    //GPS
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(context = context, perms = permissions)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        if(hasLocationPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context ?: requireContext())
            locationRequest = LocationRequest.create()
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val latLng = LatLng(locationResult.lastLocation.latitude,
                        locationResult.lastLocation.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//                placeMarker(latLng)
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                }
            }
        } else {
            findNavController().navigateUp()
        }

        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            isMyLocationEnabled = true
            setOnMarkerClickListener(this@MapsFragment)
        }
        getDeviceLocation()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val action =
            MapsFragmentDirections.actionMapsFragmentToDetailDisasterDialogFragment(marker.title)
        findNavController().navigate(action)
        return true
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        val locationResult = fusedLocationClient.lastLocation
        locationResult.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                task.result?.let { result ->
                    val latLng = LatLng(result.latitude, result.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } ?: run {
                    turningOnGPS()
                }
            }
        }
    }

    private fun placeMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions().position(latLng)
        markerOptions.title(getAddress(latLng))
        map.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder = Geocoder(requireContext())
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
            Log.e("MapsFragment", e.localizedMessage ?: "Error")
        }

        return addressText
    }

    private fun turningOnGPS() {
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        val task = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).setAlwaysShow(true).build())

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    Log.d("MapsFragmentTesting", "$fusedLocationClient")
                    it.startResolutionForResult(requireActivity(), 100)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun updateLocationAfterGPSTurnedOn() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}