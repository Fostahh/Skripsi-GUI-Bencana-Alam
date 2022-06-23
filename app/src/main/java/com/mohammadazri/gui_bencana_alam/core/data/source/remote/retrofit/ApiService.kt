package com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DetailDisasterResponse
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterItemDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/disasters")
    suspend fun getDisasters(): Response<DisastersResponse>

    @GET("/disasters/filter/{filter}")
    suspend fun getDisastersByFilter(@Path("filter") filter: String): Response<DisastersResponse>

    @GET("/disasters/{id}")
    suspend fun getDisasterById(@Path("id") id: String) : Response<DetailDisasterResponse>
}