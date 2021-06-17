package org.d3if3024.resepmakanan.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResepDao {

    @Insert
    fun insertResep(resep: ResepEntity)

    @Query("UPDATE resep SET judul=:judul where id=:id")
    fun update(judul:String, id:Long)

    @Query("Delete from resep where id =:id")
    fun delete(id:Long)

    @Query("SELECT * FROM resep")
    fun getResep():LiveData<List<ResepEntity>>

    @Query("select * from resep where id=:id")
    fun getOne(id:Long):LiveData<ResepEntity>
}