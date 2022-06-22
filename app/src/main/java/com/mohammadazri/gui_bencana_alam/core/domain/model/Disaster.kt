package com.mohammadazri.gui_bencana_alam.core.domain.model

import com.google.android.gms.maps.model.LatLng

data class Disaster(
    val id: String,
    val filter: String,
    val username: String,
    val text: String,
    val createdAt: String,
    val predicted: String,
    val mag: String? = null,
    val location: String? = null,
    val latLng: LatLng? = null
)
