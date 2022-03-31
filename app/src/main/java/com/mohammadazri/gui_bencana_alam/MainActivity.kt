package com.mohammadazri.gui_bencana_alam

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.mohammadazri.gui_bencana_alam.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityMainBinding
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestLocationPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(context = this@MainActivity, perms = permissions)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            host = this@MainActivity,
            rationale = "This app cannot work without Location Permission",
            requestCode = PERMISSION_LOCATION_REQUEST_CODE,
            perms = permissions
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(host = this@MainActivity,
                deniedPerms = perms)
        ) {
            SettingsDialog.Builder(this@MainActivity).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if(hasLocationPermission()) {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }
    }

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }


}