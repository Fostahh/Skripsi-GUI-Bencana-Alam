package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import android.util.Log
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit.ApiService
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override suspend fun getDisasters(): ApiResponse<DisastersDTO> {
        val response = apiService.getDisasters()
        return when {
            response.isSuccessful -> {
                val disasters = response.body()?.disastersDTO
                disasters?.let {
                    Log.d("RemoteDataSource", "$it")
                    ApiResponse.Success(it)
                } ?: run {
                    Log.d("RemoteDataSource", "Tidak ada bencana alam")
                    ApiResponse.Error("Tidak ada bencana alam")
                }
            }
            else -> {
                ApiResponse.Error("Terjadi kesalahan")
            }
        }
    }

    override suspend fun getDisastersByFilter(filter: String): ApiResponse<DisastersDTO?> {
        val response = apiService.getDisastersByFilter(filter)
        val disasters = response.body()?.disastersDTO
        return try {
            disasters?.let {
                Log.d("RemoteDataSource", "$it")
                ApiResponse.Success(it)
            } ?: run {
                Log.d("RemoteDataSource", "Tidak ada bencana alam")
                ApiResponse.Error("Tidak ada bencana alam")
            }
        } catch (e: Exception) {

            Log.e("RemoteDataSource", e.toString())
            ApiResponse.Error(e.toString())
        }
    }
}