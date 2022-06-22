package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersItem
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse

interface IRemoteDataSource {
    suspend fun getDisasters(): ApiResponse<List<DisastersItem?>?>
    suspend fun getDisastersByFilter(filter: String): ApiResponse<List<DisastersItem?>?>
}