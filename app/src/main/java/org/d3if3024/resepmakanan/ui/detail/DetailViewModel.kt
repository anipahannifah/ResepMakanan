package org.d3if3024.resepmakanan.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3024.resepmakanan.data.ResepDao
import org.d3if3024.resepmakanan.data.ResepEntity
import org.d3if3024.resepmakanan.internet.Api
import org.d3if3024.resepmakanan.internet.ApiStatus
import org.d3if3024.resepmakanan.internet.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val db:ResepDao): ViewModel() {
    private val hasil= MutableLiveData<DetailResponse?>()
    private val status= MutableLiveData<ApiStatus>()
    lateinit var resepDetail : LiveData<ResepEntity>


    fun dapatkanDetail(id: Long){
        resepDetail= db.getOne(id)
    }

    fun hapusResep(id: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.delete(id)
            }
        }
    }

    fun ubahJudul(id: Long, judul: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.update(judul, id)
            }
        }
    }

    fun cariDetail(id: Int){
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            try {
                Api.instance.getDetails(id)
                    .enqueue(object : Callback<DetailResponse> {
                        override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                            if (response.isSuccessful) {
                                hasil.value = response.body()
                                Log.d("DetailViewModel", "res $hasil, id: $id")
                                status.value= ApiStatus.SUCCESS
                            }else{
                                status.value = ApiStatus.FAILED
                                Log.d("DetailViewModel", "res $hasil, id: $id")
                            }
                        }
                        override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                            status.value = ApiStatus.FAILED
                        }
                    })
            } catch (e: Exception) {
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun simpanResep( judul: String,deskripsi: String,link: String, image: String?){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val resep= ResepEntity(
                    judul = judul,
                    deskripsi = deskripsi,
                    link = link,
                    image = image
                )
                db.insertResep(resep)
            }
        }
    }

    fun getResep(): LiveData<DetailResponse?> =hasil
    fun getStatus(): LiveData<ApiStatus> =status

}