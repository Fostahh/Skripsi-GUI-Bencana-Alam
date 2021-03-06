package com.mohammadazri.gui_bencana_alam.ui.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.databinding.FragmentNeedPermissionsBinding
import com.mohammadazri.gui_bencana_alam.util.Constant.PERMISSION_LOCATION_REQUEST_CODE
import com.mohammadazri.gui_bencana_alam.util.PermissionUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class NeedPermissionsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentNeedPermissionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNeedPermissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            checkPermission()

            binding.buttonIzinkan.setOnClickListener {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (PermissionUtility.isPermissionsGranted(requireContext())) {
            findNavController().navigate(R.id.action_needPermissionsFragment_to_mapsFragment)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this,
                    "This app cannot work without Location Permission",
                    PERMISSION_LOCATION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this,
                    "This app cannot work without Location Permission",
                    PERMISSION_LOCATION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }

            with(binding) {
                imageViewNeedPermission.visibility = View.VISIBLE
                textViewNeedPermissionDesc.visibility = View.VISIBLE
                buttonIzinkan.visibility = View.VISIBLE
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            checkPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        findNavController().navigate(R.id.action_needPermissionsFragment_to_mapsFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }
}