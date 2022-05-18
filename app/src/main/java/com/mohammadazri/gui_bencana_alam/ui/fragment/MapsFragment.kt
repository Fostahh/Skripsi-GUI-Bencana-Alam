package com.mohammadazri.gui_bencana_alam.ui.fragment

import android.annotation.SuppressLint
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mohammadazri.gui_bencana_alam.GeofenceHelper
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.databinding.FragmentMapsBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import com.mohammadazri.gui_bencana_alam.util.Constant
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_DOUBLE
import com.mohammadazri.gui_bencana_alam.util.Constant.TURN_ON_GPS_REQUEST_CODE
import com.mohammadazri.gui_bencana_alam.util.PermissionUtility
import com.mohammadazri.gui_bencana_alam.util.ext.toMapLatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by viewModels()

    private lateinit var map: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var geofenceHelper: GeofenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

        viewModel.getCurrentLocation().observe(viewLifecycleOwner) {
            it?.let { latLng ->
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            } ?: run {
                turnOnGPSDialog()
            }
        }

        viewModel.getDisasters().observe(viewLifecycleOwner) { disastersDTO ->
            disastersDTO?.disasters?.let { disasters ->

                addGeofence(disasters)
                placeMarker(disasters)
                addCircle(disasters)
                disasters.forEach {
                    it?.let { disasterDTO ->
                        disasterDTO.latLng?.let { latLng ->
//                            placeMarker(latLng.toMapLatLng())
//                            addCircle(latLng.toMapLatLng())
                        }
                    }
                }
            }
        }
    }

    private fun placeMarker(latLng: List<DisasterDTO?>) {
        val markerOptions = MarkerOptions()
        markerOptions.apply {
            latLng.forEach { a ->
                a?.let { disasterDTO ->
                    disasterDTO.latLng?.let {
                        position(it.toMapLatLng())
                        title(viewModel.getAddress(it.toMapLatLng(), requireContext()))
                        map.addMarker(this)
                    }
                }
            }
//            position(latLng)
//            title(viewModel.getAddress(latLng, requireContext()))
//            map.addMarker(this)
        }
    }

    private fun turnOnGPSDialog() {
        val task = LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest.create())
                .setAlwaysShow(true)
                .build())

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(requireActivity(), TURN_ON_GPS_REQUEST_CODE)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val action =
            MapsFragmentDirections.actionMapsFragmentToDetailDisasterDialogFragment(
                marker.title)
        findNavController().navigate(action)
        return true
    }

    private fun addCircle(latLng: List<DisasterDTO?>) {
        val circleOptions = CircleOptions()
        circleOptions.apply {
            latLng.forEach { a ->
                a?.let { disasterDTO ->
                    disasterDTO.latLng?.let {
                        center(it.toMapLatLng())
                        radius(GEOFENCE_RADIUS_DOUBLE)
                        strokeColor(Color.argb(255, 255, 0, 0))
                        fillColor(Color.argb(64, 255, 0, 0))
                        strokeWidth(4F)
                        map.addCircle(circleOptions)
                    }
                }
            }
//            center(latLng)
//            radius(GEOFENCE_RADIUS_DOUBLE)
//            strokeColor(Color.argb(255, 255, 0, 0))
//            fillColor(Color.argb(64, 255, 0, 0))
//            strokeWidth(4F)
//            map.addCircle(circleOptions)
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(listDisasterDTO: List<DisasterDTO?>) {
        val geofencingRequest = geofenceHelper.getGeofencingRequest(listDisasterDTO)
        val pendingIntent = geofenceHelper.getPendingIntent()

        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
            addOnSuccessListener {
                Toast.makeText(requireContext(), "Geofences added", Toast.LENGTH_SHORT).show()
            }
            addOnFailureListener {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

//    private fun removeGeofence() {
//        geofencingClient.removeGeofences(geofenceIntent).run {
//            addOnSuccessListener {
//                Toast.makeText(requireContext(), "Geofences removed", Toast.LENGTH_SHORT).show()
//            }
//            addOnFailureListener {
//                Toast.makeText(requireContext(), "Failed to remove geofences", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        if (!PermissionUtility.isPermissionsGranted(requireContext())) {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}