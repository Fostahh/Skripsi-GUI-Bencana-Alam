package com.mohammadazri.gui_bencana_alam.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.mohammadazri.gui_bencana_alam.databinding.ActivityMainBinding
import com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel.SharedViewModel
import com.mohammadazri.gui_bencana_alam.util.Constant.TURN_ON_GPS_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TURN_ON_GPS_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    viewModel.getCurrentLocation()
                    viewModel.getDisasters()
                }
                Activity.RESULT_CANCELED -> Toast.makeText(this,
                    "GPS is required",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}