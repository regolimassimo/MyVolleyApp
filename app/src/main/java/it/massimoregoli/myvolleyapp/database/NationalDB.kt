package it.massimoregoli.myvolleyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import it.massimoregoli.myvolleyapp.model.National

@Database(entities = [National::class], version=1)
abstract class NationalDB: RoomDatabase(){
    companion object {
        @Volatile
        private var db: NationalDB? = null

        fun getDatabase(context: Context): NationalDB {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    NationalDB::class.java,
                    "nationaldb.db"
                ).build()
            }
            return db as NationalDB
        }
    }
}