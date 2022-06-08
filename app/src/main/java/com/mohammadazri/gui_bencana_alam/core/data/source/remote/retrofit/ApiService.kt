package com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/disasters")
    fun getDisasters(): Call<DisastersResponse>

    @GET("/disasters/filter/{filter}")
    fun getDisastersByFilter(@Path("filter") filter: String?): Call<DisastersResponse>
}