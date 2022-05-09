package com.mohammadazri.gui_bencana_alam.core.domain.model

import com.google.android.gms.maps.model.LatLng

data class Disaster(
    val latLng: LatLng? = null,
    val type: String? = null
)
