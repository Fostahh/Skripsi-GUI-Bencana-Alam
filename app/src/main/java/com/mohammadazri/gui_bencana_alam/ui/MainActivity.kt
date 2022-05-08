package com.mohammadazri.gui_bencana_alam

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.mohammadazri.gui_bencana_alam.databinding.ActivityMainBinding
import com.mohammadazri.gui_bencana_alam.fragment.MapsFragment
import com.mohammadazri.gui_bencana_alam.fragment.NeedPermissionsFragment
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private val permissions = arrayOf(
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_COARSE_LOCATION
//    )

//    lateinit var navHostFragment: NavHostFragment
//    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        navController = navHostFragment.navController


//        if(hasLocationPermission()) {
//            navController.navigate(R.id.action_needPermissionsFragment_to_mapsFragment)
//        } else {
//            requestLocationPermission()
//        }
    }

//    private fun requestLocationPermission() {
//        EasyPermissions.requestPermissions(
//            host = this,
//            rationale = "This app cannot work without Location Permission",
//            requestCode = PERMISSION_LOCATION_REQUEST_CODE,
//            perms = permissions
//        )
//    }
//
//    private fun hasLocationPermission() =
//        EasyPermissions.hasPermissions(context = this, perms = permissions)
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
//
//    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(host = this,
//                deniedPerms = perms)
//        ) {
//            SettingsDialog.Builder(this).build().show()
//        } else {
//            requestLocationPermission()
//        }
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Toast.makeText(this,
                        "GPS is Turned on",
                        Toast.LENGTH_SHORT).show()
                }
                Activity.RESULT_CANCELED -> Toast.makeText(this,
                    "GPS is required",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

//    companion object {
//        const val PERMISSION_LOCATION_REQUEST_CODE = 1
//    }
}