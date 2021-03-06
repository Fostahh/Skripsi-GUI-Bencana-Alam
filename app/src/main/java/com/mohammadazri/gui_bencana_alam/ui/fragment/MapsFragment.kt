package com.mohammadazri.gui_bencana_alam.ui.fragment

import android.annotation.SuppressLint
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mohammadazri.gui_bencana_alam.GeofenceHelper
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.databinding.FragmentMapsBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import com.mohammadazri.gui_bencana_alam.util.Constant.CIRCLE_RADIUS_DOUBLE
import com.mohammadazri.gui_bencana_alam.util.Constant.CIRCLE_RADIUS_DOUBLE_GREAT
import com.mohammadazri.gui_bencana_alam.util.Constant.CIRCLE_RADIUS_DOUBLE_MAJOR
import com.mohammadazri.gui_bencana_alam.util.Constant.CIRCLE_RADIUS_DOUBLE_MINOR
import com.mohammadazri.gui_bencana_alam.util.Constant.CIRCLE_RADIUS_DOUBLE_MODERATE
import com.mohammadazri.gui_bencana_alam.util.Constant.TURN_ON_GPS_REQUEST_CODE
import com.mohammadazri.gui_bencana_alam.util.PermissionUtility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var map: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        geofenceHelper = GeofenceHelper(requireContext())
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            isMyLocationEnabled = true
            setOnMarkerClickListener(this@MapsFragment)
        }

        observeLiveData()

        viewModel.getCurrentLocation().observe(viewLifecycleOwner) {
            it?.let { latLng ->
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            } ?: run {
                turnOnGPSDialog()
            }
        }

        viewModel.getDisasters()
    }

    private fun observeLiveData() {
        viewModel.listDisasterLiveData.observe(viewLifecycleOwner) {
            map.clear()
            addGeofence(it)
            it.map { disaster ->

                placeMarker(disaster)
                addCircle(disaster)
            }
        }

        viewModel.filteredLiveData.observe(viewLifecycleOwner) {
            map.clear()
            addGeofence(it)
            it.map { disaster ->
                placeMarker(disaster)
                addCircle(disaster)
            }
        }

        viewModel.toastErrorLiveData.observe(viewLifecycleOwner) {
            it?.let { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(listDisaster: List<Disaster>) {
        val geofencingRequest = geofenceHelper.getGeofencingRequest(listDisaster)
        val pendingIntent by lazy { geofenceHelper.getPendingIntent() }

        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
//            addOnSuccessListener {
//                Toast.makeText(requireContext(), "Geofence berhasil", Toast.LENGTH_SHORT)
//                    .show()
//            }
            addOnFailureListener {
                Toast.makeText(requireContext(), "Mohon beri izin setiap saat", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun placeMarker(disaster: Disaster) {
        val markerOptions = MarkerOptions()
        markerOptions.apply {
            disaster.latLng?.let { latLng ->
                position(latLng)
            }
            if (disaster.filter == "gempa") {
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            } else {
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            }
            title(disaster.id)
            map.addMarker(this)
        }
    }

    private fun turnOnGPSDialog() {
        val task = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(LocationRequest.create())
                    .setAlwaysShow(true)
                    .build()
            )

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(requireActivity(), TURN_ON_GPS_REQUEST_CODE)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    private fun addCircle(disaster: Disaster) {
        val circleOptions = CircleOptions()
        circleOptions.apply {
            disaster.latLng?.let { latLng ->
                center(latLng)
            }
            disaster.mag?.let { mag ->
                when {
                    mag.toDouble() in 2.0..3.9 -> {
                        strokeColor(Color.argb(255, 0, 0, 255))
                        fillColor(Color.argb(64, 0, 0, 255))
                        radius(CIRCLE_RADIUS_DOUBLE_MINOR)
                    }
                    mag.toDouble() in 4.0..5.9 -> {
                        strokeColor(Color.argb(255, 0, 255, 0))
                        fillColor(Color.argb(64, 0, 255, 0))
                        radius(CIRCLE_RADIUS_DOUBLE_MODERATE)
                    }
                    mag.toDouble() in 6.0..7.9 -> {
                        strokeColor(Color.argb(255, 255, 165, 0))
                        fillColor(Color.argb(64, 255, 165, 0))
                        radius(CIRCLE_RADIUS_DOUBLE_MAJOR)
                    }
                    mag.toDouble() >= 8 -> {
                        strokeColor(Color.argb(255, 255, 0, 0))
                        fillColor(Color.argb(64, 255, 0, 0))
                        radius(CIRCLE_RADIUS_DOUBLE_GREAT)
                    }
                    else -> {
                        radius(CIRCLE_RADIUS_DOUBLE)
                        strokeColor (Color.argb(255, 255, 0, 0))
                        fillColor(Color.argb(64, 255, 0, 0))
                    }
                }
            } ?: run {
                radius(CIRCLE_RADIUS_DOUBLE)
                strokeColor(Color.argb(255, 0, 255, 255))
                fillColor(Color.argb(64, 0, 255, 255))
            }
            strokeWidth(4F)
            map.addCircle(circleOptions)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val action =
            MapsFragmentDirections.actionMapsFragmentToDetailDisasterDialogFragment(marker.title)
        findNavController().navigate(action)
        return true
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        if (!PermissionUtility.isPermissionsGranted(requireContext())) {
            findNavController().navigateUp()
        } else {
            viewModel.resumeLocationUpdates()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}