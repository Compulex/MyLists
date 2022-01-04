package com.example.mylists.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@Entity(foreignKeys = [ForeignKey(entity = MyList::class, parentColumns = arrayOf("id"),
    //childColumns = arrayOf("list_id"), onDelete = ForeignKey.CASCADE)]
@Entity(tableName = "item")
    class Item(
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "quantity") var quantity : Int,
    @ColumnInfo(name = "specifics") var specifics : String,
    @ColumnInfo(name = "isChecked") var isChecked : Boolean,
    @ColumnInfo(name = "listId") var listId: Long) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}