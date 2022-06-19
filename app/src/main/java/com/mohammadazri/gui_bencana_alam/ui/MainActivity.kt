package com.mohammadazri.gui_bencana_alam.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohammadazri.gui_bencana_alam.R
import com.mohammadazri.gui_bencana_alam.databinding.ActivityMainBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import com.mohammadazri.gui_bencana_alam.util.Constant.TURN_ON_GPS_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.needPermissionsFragment, R.id.aboutUsFragment -> hideSideMenu()
                else -> showSideMenu()
            }
        }

        binding.sideMenu.setOnClickListener {
            binding.drawerLayoutMain.openDrawer(GravityCompat.START)
        }

        binding.navViewMaps.setNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.peta -> viewModel.getDisasters()
                R.id.filter_gempa -> viewModel.getDisastersByFilter("gempa")
                R.id.filter_banjir -> viewModel.getDisastersByFilter("banjir")
                R.id.about_us -> navController.navigate(R.id.aboutUsFragment)
            }

            binding.drawerLayoutMain.closeDrawers()

            true
        }
    }

    private fun hideSideMenu() {
        binding.appBarLayoutMaps.visibility = View.GONE
        binding.navViewMaps.visibility = View.GONE
        binding.drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun showSideMenu() {
        binding.appBarLayoutMaps.visibility = View.VISIBLE
        binding.navViewMaps.visibility = View.VISIBLE
        binding.drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TURN_ON_GPS_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    viewModel.getCurrentLocation()
                    viewModel.getDisasters()
                }
                Activity.RESULT_CANCELED -> Toast.makeText(
                    this,
                    "GPS is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.aboutUsFragment) navController.popBackStack()
    }
}