package com.mohammadazri.gui_bencana_alam.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DisastersResponse(
    @field:SerializedName("data")
    val data: List<DisastersItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,
)

data class DisastersItem(
    @field:SerializedName("filter")
    val filter: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("mag")
    val mag: String? = null,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("predicted")
    val predicted: String,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("lon")
    val lon: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null,
)

