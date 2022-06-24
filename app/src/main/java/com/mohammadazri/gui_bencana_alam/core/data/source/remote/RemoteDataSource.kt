package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import android.util.Log
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterItemDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit.ApiService
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override suspend fun getDisasters(): ApiResponse<List<DisasterItemDTO?>?> {
        val response = apiService.getDisasters()
        return when {
            response.isSuccessful -> {
                val bodyResponse = response.body()
                val disasters = bodyResponse?.data
                val message = bodyResponse?.message

                disasters?.let {
                    ApiResponse.Success(it)
                } ?: run {
                    ApiResponse.Error(message ?: "Bencana Alam tidak ditemukan")
                }
            }
            else -> {
                ApiResponse.Error("Terjadi kesalahan pada server")
            }
        }
    }

    override suspend fun getDisastersByFilter(filter: String): ApiResponse<List<DisasterItemDTO?>?> {
        val response = apiService.getDisastersByFilter(filter)
        return when {
            response.isSuccessful -> {
                val bodyResponse = response.body()
                val disasters = bodyResponse?.data
                val message = bodyResponse?.message

                disasters?.let {
                    ApiResponse.Success(it)
                } ?: run {
                    ApiResponse.Error(message ?: "Bencana Alam tidak ditemukan")
                }
            }
            else -> ApiResponse.Error("Terjadi kesalahan pada server")
        }
    }

    override suspend fun getDisasterById(id: String): ApiResponse<DisasterItemDTO?> {
        val response = apiService.getDisasterById(id)
        return when {
            response.isSuccessful -> {
                val disaster = response.body()?.data

                disaster?.let {
                    Log.d("DataRemote", "$it")
                    ApiResponse.Success(it)
                } ?: run {
                    ApiResponse.Error("Bencana Alam tidak ditemukan")
                }
            }
            else -> ApiResponse.Error("Terjadi kesalahan pada server")
        }
    }
}