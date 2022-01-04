package com.example.mylists.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.mylists.entities.MyList

@Dao
interface ListDao {
    @Query("select * from list")
    fun getAllLists(): List<MyList>

    @Query("select * from list where id = :id")
    fun getListById(id: Long): MyList

    @Insert(onConflict = REPLACE)
    fun insertList(myList: MyList)

    @Update(onConflict = REPLACE)
    fun updateList(myList: MyList)

    @Delete
    fun deleteList(myList: MyList)
}