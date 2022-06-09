package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import android.util.Log
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit.ApiService
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override suspend fun getDisasters(): Flow<ApiResponse<DisastersDTO>> {
        return flow {
            Log.d("ViewModel", "RemoteDataSource")
            val response = apiService.getDisasters()
            if (response.isSuccessful) {
                val disasters = response.body()?.disastersDTO
                disasters?.let {
                    emit(ApiResponse.Success(it))
                    Log.d("RemoteDataSource", "$it")
                } ?: run {
                    emit(ApiResponse.Error("Tidak ada bencana alam"))
                    Log.d("RemoteDataSource", "Tidak ada bencana alam")
                }
            } else {
                emit(ApiResponse.Error("Terjadi kesalahan"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDisastersByFilter(filter: String): Flow<ApiResponse<DisastersDTO?>> =
        flow<ApiResponse<DisastersDTO?>> {
            Log.d("ViewModelFilter", "RemoteDataSource")
            val response = apiService.getDisastersByFilter(filter)
            val disasters = response.disastersDTO
            try {
                disasters?.let {
                    emit(ApiResponse.Success(it))
                    Log.d("RemoteDataSource", "$it")
                } ?: run {
                    emit(ApiResponse.Error("Tidak ada bencana alam"))

                    Log.d("RemoteDataSource", "Tidak ada bencana alam")
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))

                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
}