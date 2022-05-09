package com.mohammadazri.gui_bencana_alam.util.ext

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.LatLng

fun LatLng.toMapLatLng() : com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(this.latitude!!, this.longitude!!)
}