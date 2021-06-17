package org.d3if3024.resepmakanan.ui.cari

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.d3if3024.resepmakanan.internet.Api
import org.d3if3024.resepmakanan.internet.ApiResponse
import org.d3if3024.resepmakanan.internet.ApiStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CariViewModel :ViewModel(){
    private val hasil=MutableLiveData<ApiResponse?>()
    private val status=MutableLiveData<ApiStatus>()

    fun cariResep(query: String){
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                Api.instance.get(query)
                        .enqueue(object : Callback<ApiResponse> {
                            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                                if (response.isSuccessful) {
                                    hasil.value = response.body()
                                    if(hasil.value?.results!!.isEmpty()){
                                        status.value= ApiStatus.NOTFOUND
                                    }else{
                                        status.value= ApiStatus.SUCCESS
                                    }
                                }else{
                                    status.value = ApiStatus.FAILED
                                }
                            }
                            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                                status.value = ApiStatus.FAILED
                            }
                        })
            } catch (e: Exception) {
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun getResep(): LiveData<ApiResponse?> =hasil
    fun getStatus(): LiveData<ApiStatus> =status
}