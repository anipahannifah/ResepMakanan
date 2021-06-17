package org.d3if3024.resepmakanan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resep")
data class ResepEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,
        var judul: String?,
        var deskripsi: String?,
        var link: String?,
        var image: String?
)