package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterItemDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse

interface IRemoteDataSource {
    suspend fun getDisasters(): ApiResponse<List<DisasterItemDTO?>?>
    suspend fun getDisastersByFilter(filter: String): ApiResponse<List<DisasterItemDTO?>?>
    suspend fun getDisasterById(id: String) : ApiResponse<DisasterItemDTO?>
}