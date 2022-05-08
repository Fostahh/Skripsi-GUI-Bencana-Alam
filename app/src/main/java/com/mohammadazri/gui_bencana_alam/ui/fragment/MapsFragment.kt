package com.mohammadazri.gui_bencana_alam.ui.fragment

import android.annotation.SuppressLint
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.databinding.FragmentMapsBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import com.mohammadazri.gui_bencana_alam.util.PermissionUtility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap

    private val viewModel: SharedViewModel by viewModels()

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
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            isMyLocationEnabled = true
            setOnMarkerClickListener(this@MapsFragment)
        }
        viewModel.getCurrentLocation().observe(viewLifecycleOwner) { latLng ->

            latLng?.let {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                Log.d("MapsFragmentTesting", "$latLng")
                placeMarker(it)
            } ?: run {
                turnOnGPSDialog()
            }
        }
    }

    private fun placeMarker(latLng: LatLng) {
        val markerOptions = MarkerOptions().position(latLng)
        markerOptions.title(viewModel.getAddress(latLng, requireContext()))
        map.addMarker(markerOptions)
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
                    it.startResolutionForResult(requireActivity(), 100)
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

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
        Log.d("MapsFragmentPause", "Masuk")
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