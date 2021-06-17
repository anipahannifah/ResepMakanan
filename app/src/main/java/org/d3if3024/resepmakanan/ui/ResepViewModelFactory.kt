package org.d3if3024.resepmakanan.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3024.resepmakanan.data.ResepDao
import org.d3if3024.resepmakanan.ui.detail.DetailViewModel

class ResepViewModelFactory (private val db: ResepDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResepViewModel::class.java)) {
            return ResepViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}