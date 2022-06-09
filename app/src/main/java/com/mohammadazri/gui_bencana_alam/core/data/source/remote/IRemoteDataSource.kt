package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import androidx.lifecycle.LiveData
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getDisasters(): Flow<ApiResponse<DisastersDTO?>>
    suspend fun getDisastersByFilter(filter: String): Flow<ApiResponse<DisastersDTO?>>
}