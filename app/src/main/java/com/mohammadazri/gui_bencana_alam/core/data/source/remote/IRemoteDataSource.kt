package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import androidx.lifecycle.LiveData
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse

interface IRemoteDataSource {
    fun getDisasters(): LiveData<ApiResponse<DisastersDTO?>>
}