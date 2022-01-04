package com.example.mylists.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.mylists.entities.Item

@Dao
interface ItemDao {
    @Query("select * from item where listId = :listId")
    fun getByListId(listId: Long): List<Item>

    @Insert(onConflict = REPLACE)
    fun insertItem(item: Item)

    @Update(onConflict = REPLACE)
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)
}