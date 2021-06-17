package org.d3if3024.resepmakanan.ui

import androidx.lifecycle.ViewModel
import org.d3if3024.resepmakanan.data.ResepDao

class ResepViewModel(private val db: ResepDao): ViewModel() {
    val resep = db.getResep()

}