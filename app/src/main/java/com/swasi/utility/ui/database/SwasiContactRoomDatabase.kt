package com.swasi.utility.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.swasi.utility.model.ContactEntity
import com.swasi.utility.model.VideoEntity

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [ContactEntity::class, VideoEntity::class], version = 1, exportSchema = false)
abstract class SwasiContactRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
    abstract fun videoDao(): VideoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time. 
        @Volatile
        private var INSTANCE: SwasiContactRoomDatabase? = null

        fun getDatabase(context: Context): SwasiContactRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SwasiContactRoomDatabase::class.java,
                    "swasiutility_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
