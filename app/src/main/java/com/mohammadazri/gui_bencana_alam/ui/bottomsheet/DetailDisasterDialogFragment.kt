package com.mohammadazri.gui_bencana_alam.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohammadazri.gui_bencana_alam.databinding.FragmentDetailDisasterDialogBinding


class DetailDisasterDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailDisasterDialogBinding? = null
    private val binding get() = _binding!!
    private val args: DetailDisasterDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailDisasterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView.text = args.disaster
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}