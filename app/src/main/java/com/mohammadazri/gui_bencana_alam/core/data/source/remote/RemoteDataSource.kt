package com.mohammadazri.gui_bencana_alam.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersResponse
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) : IRemoteDataSource {
    override fun getDisasters(): LiveData<DisastersDTO?> {
        val result = MutableLiveData<DisastersDTO?>()

        apiService.getDisasters().enqueue(object : Callback<DisastersResponse> {
            override fun onResponse(
                call: Call<DisastersResponse>,
                response: Response<DisastersResponse>,
            ) {
                if(response.isSuccessful) {
                    val data = response.body()?.disastersDTO
                    data?.let {
                        result.postValue(it)
                        Log.d("RemoteDataSource", "Ada data! $it")
                    } ?: run {
                        result.postValue(null)
                        Log.d("RemoteDataSource", "Kosong ya!")
                    }
                }
            }

            override fun onFailure(call: Call<DisastersResponse>, t: Throwable) {
                result.postValue(null)
                Log.d("RemoteDataSource", "Error!")
            }

        })

        return result
    }
}