package org.d3if3024.resepmakanan.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3024.resepmakanan.data.ResepDao

class DetailViewModelFactory(private val db:ResepDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}