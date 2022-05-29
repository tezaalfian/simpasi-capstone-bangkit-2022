package com.tezaalfian.simpasi.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tezaalfian.simpasi.core.data.source.local.entity.ChildEntity

@Database(
    entities = [ChildEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SimpasiDatabase : RoomDatabase() {
    abstract fun storyDao(): ChildDao

    companion object {
        @Volatile
        private var INSTANCE: SimpasiDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SimpasiDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SimpasiDatabase::class.java, "simpasi_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}