package com.mohammadazri.gui_bencana_alam.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohammadazri.gui_bencana_alam.MainActivity
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.databinding.FragmentNeedPermissionsBinding
import com.mohammadazri.gui_bencana_alam.util.Constant
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog


class NeedPermissionsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentNeedPermissionsBinding? = null
    private val binding get() = _binding!!
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

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

            binding.button.setOnClickListener {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (hasLocationPermission()) {
            findNavController().navigate(R.id.action_needPermissionsFragment_to_mapsFragment)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            host = this,
            rationale = "This app cannot work without Location Permission",
            requestCode = PERMISSION_LOCATION_REQUEST_CODE,
            perms = permissions
        )
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(context = context, perms = permissions)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(host = this,
                deniedPerms = perms)
        ) {
            SettingsDialog.Builder(context ?: requireContext()).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        findNavController().navigate(R.id.action_needPermissionsFragment_to_mapsFragment)
    }

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

}