package com.mohammadazri.gui_bencana_alam.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DisastersResponse(
    @field:SerializedName("data")
    val disastersDTO: DisastersDTO? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DisastersDTO(
    @field:SerializedName("disasters")
    val disasters: List<DisasterDTO?>? = null
)

data class DisasterDTO(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("Latlng")
    val latLng: LatLng? = null,

    @field:SerializedName("type")
    val type: String? = null
)

data class LatLng(
    @field:SerializedName("_latitude")
    val latitude: Double? = null,

    @field:SerializedName("_longitude")
    val longitude: Double? = null
)

