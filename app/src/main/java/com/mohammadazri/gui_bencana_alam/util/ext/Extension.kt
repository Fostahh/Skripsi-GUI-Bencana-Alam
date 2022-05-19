package com.mohammadazri.gui_bencana_alam.util.ext

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

fun LatLng.toMapLatLng() : com.google.android.gms.maps.model.LatLng {
    return com.google.android.gms.maps.model.LatLng(this.latitude!!, this.longitude!!)
}