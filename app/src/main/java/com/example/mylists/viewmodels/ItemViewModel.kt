package com.example.mylists.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mylists.database.ListDatabase
import com.example.mylists.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private var allItems : MutableList<Item> = mutableListOf()
    private var mAllItems : MutableLiveData<MutableList<Item>> = MutableLiveData()
    private val db = ListDatabase.getInstance(this.getApplication())
    var listId : Long = 0

    init {
        viewModelScope.launch(Dispatchers.IO){
            allItems = db.itemDao().getByListId(listId).toMutableList()
            mAllItems.postValue(allItems)
        }
    }

    fun getItems(): MutableLiveData<MutableList<Item>>{ return mAllItems }//getItems

    fun insertItem(item: Item){
        viewModelScope.launch(Dispatchers.IO){
            db.itemDao().insertItem(item)
            allItems = db.itemDao().getByListId(listId).toMutableList()
            mAllItems.postValue(allItems)
        }
    }//insertItem

    fun updateItem(item: Item){
        viewModelScope.launch(Dispatchers.IO){
            db.itemDao().updateItem(item)
            allItems = db.itemDao().getByListId(listId).toMutableList()
            mAllItems.postValue(allItems)
        }
    }//updateItem

    fun deleteItem(item: Item){
        viewModelScope.launch(Dispatchers.IO){
            db.itemDao().deleteItem(item)
            allItems = db.itemDao().getByListId(listId).toMutableList()
            mAllItems.postValue(allItems)
        }
    }//deleteItem
}