package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import androidx.lifecycle.LiveData
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse

interface IRemoteDataSource {
    fun getDisasters(): LiveData<ApiResponse<DisastersDTO?>>
    fun getDisastersByFilter(filter: String?): LiveData<ApiResponse<DisastersDTO?>>
}