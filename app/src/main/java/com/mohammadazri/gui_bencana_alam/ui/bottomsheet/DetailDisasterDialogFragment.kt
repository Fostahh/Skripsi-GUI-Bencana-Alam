package com.mohammadazri.gui_bencana_alam.ui.bottomsheet

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
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
                                disaster.latLng?.let { latLng ->
                                    viewModel.getAddress(latLng, requireContext())
                                } ?: run {
                                    "Lokasi tidak diketahui"
                                }
                            }

                            textViewDisasterTimeValue.text =
                                disaster.createdAt.subSequence(0, 20).trim()

                            if (disaster.filter == "gempa") {
                                textViewDisasterMagnitudeValue.text = disaster.mag?.let { mag ->
                                    mag
                                } ?: run {
                                    "0"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}