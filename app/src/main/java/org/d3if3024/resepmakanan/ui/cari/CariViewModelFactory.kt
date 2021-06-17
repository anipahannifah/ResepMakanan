package org.d3if3024.resepmakanan.ui.cari

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CariViewModelFactory :ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(CariViewModel::class.java)) {
            return CariViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}