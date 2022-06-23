package com.mohammadazri.gui_bencana_alam.ui.bottomsheet

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohammadazri.gui_bencana_alam.databinding.FragmentDetailDisasterDialogBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*


class DetailDisasterDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailDisasterDialogBinding? = null
    private val binding get() = _binding!!
    private val args: DetailDisasterDialogFragmentArgs by navArgs()
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailDisasterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->
            args.id?.let { id ->
                viewModel.getDisasterById(id)
                viewModel.disasterLiveData.observe(viewLifecycleOwner) {
                    it?.let { disaster ->
                        with(binding) {
                            textViewDisasterCategory.text =
                                disaster.filter.replaceFirstChar { firstChar ->
                                    firstChar.uppercase()
                                }

                            textViewDisasterLocationValue.text = disaster.location?.let {
                                it
                            } ?: run {
                                viewModel.getAddress(disaster.latLng!!, requireContext())
                            }

                            textViewDisasterTimeValue.text =
                                disaster.createdAt.subSequence(0, 20).trim()

                            if (disaster.filter == "gempa") {
                                textViewDisasterMagnitudeValue.text = disaster.mag?.let {
                                    it
                                }
                                textViewDisasterMagnitude.visibility = View.VISIBLE
                                textViewDisasterMagnitudeValue.visibility = View.VISIBLE
                            } else {
                                textViewDisasterMagnitude.visibility = View.GONE
                                textViewDisasterMagnitudeValue.visibility = View.GONE
                            }
                        }
                    }
                }
            } ?: run {
                Toast.makeText(requireContext(), "Bencana Alam tidak ditemukan", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
        }
    }

    fun getAddress(latLng: LatLng) {
        val geocoder = Geocoder(requireContext())
        val address: Address?
        var fulladdress = ""
        val addresses: List<Address>? =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        addresses?.let {
            if (addresses.isNotEmpty()) {
                address = addresses[0]
                fulladdress =
                    address.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex
                var city = address.locality;
                var state = address.adminArea;
                var country = address.countryName;
                var postalCode = address.postalCode;
                var knownName = address.featureName; // Only if available else return NULL
            } else {
                fulladdress = "Location not found"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}