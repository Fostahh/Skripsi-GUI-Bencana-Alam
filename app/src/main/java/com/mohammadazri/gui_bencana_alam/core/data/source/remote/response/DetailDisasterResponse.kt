package com.mohammadazri.gui_bencana_alam.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailDisasterResponse(
    @field:SerializedName("data")
    val data: DisasterItemDTO? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,
)