package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import android.util.Log
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersItem
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit.ApiService
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override suspend fun getDisasters(): ApiResponse<List<DisastersItem?>?> {
        val response = apiService.getDisasters()
        return when {
            response.isSuccessful -> {
                val bodyResponse = response.body()
                val disasters = bodyResponse?.data
                val message = bodyResponse?.message

                disasters?.let {
                    ApiResponse.Success(it)
                } ?: run {
                    ApiResponse.Error(message ?: "Terjadi kesalahan")
                }
            }
            else -> {
                ApiResponse.Error("Terjadi kesalahan pada server")
            }
        }
    }

    override suspend fun getDisastersByFilter(filter: String): ApiResponse<List<DisastersItem?>?> {
        val response = apiService.getDisastersByFilter(filter)
        return when {
            response.isSuccessful -> {
                val bodyResponse = response.body()
                val disasters = bodyResponse?.data
                val message = bodyResponse?.message

                disasters?.let {
                    ApiResponse.Success(disasters)
                } ?: run {
                    ApiResponse.Error(message ?: "Terjadi kesalahan")
                }
            }
            else -> ApiResponse.Error("Terjadi kesalahan pada server")
        }
    }
}