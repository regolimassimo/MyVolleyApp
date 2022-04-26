package it.massimoregoli.myvolleyapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import it.massimoregoli.myvolleyapp.model.National

@Dao
interface NationalDao {
    @Insert
    fun insertAll(data: List<National> )
    @Query ("SELECT * FROM national order by data")
    fun getAll(): LiveData<List<National>>
}