package com.example.mylists.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylists.dao.ItemDao
import com.example.mylists.dao.ListDao
import com.example.mylists.entities.Item
import com.example.mylists.entities.MyList

@Database(entities = [MyList::class, Item::class], version = 1, exportSchema = false)
abstract class ListDatabase: RoomDatabase() {
    abstract fun listDao(): ListDao

    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile
        private var INSTANCE: ListDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ListDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ListDatabase::class.java, "lists.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE!!
        }
    }
}