package com.example.mylists.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mylists.database.ListDatabase
import com.example.mylists.entities.MyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): AndroidViewModel(application) {
    private var allLists : MutableList<MyList> = mutableListOf()
    private var mAllLists : MutableLiveData<MutableList<MyList>> = MutableLiveData()
    private val db = ListDatabase.getInstance(this.getApplication())

    init {
        viewModelScope.launch(Dispatchers.IO){
            allLists = db.listDao().getAllLists().toMutableList()
            mAllLists.postValue(allLists)
        }
    }

    fun getLists(): MutableLiveData<MutableList<MyList>>{ return mAllLists }

    fun getListById(id: Long): MyList{
        return db.listDao().getListById(id)
    }//getListById

    fun insertList(myList: MyList){
        viewModelScope.launch(Dispatchers.IO){
            db.listDao().insertList(myList)
            allLists = db.listDao().getAllLists().toMutableList()
            mAllLists.postValue(allLists)
        }
    }//insertList

    fun updateList(myList: MyList){
        viewModelScope.launch(Dispatchers.IO){
            db.listDao().updateList(myList)
            allLists = db.listDao().getAllLists().toMutableList()
            mAllLists.postValue(allLists)
        }
    }//updateList

    fun deleteList(myList: MyList){
        viewModelScope.launch(Dispatchers.IO){
            db.listDao().deleteList(myList)
            allLists = db.listDao().getAllLists().toMutableList()
            mAllLists.postValue(allLists)
        }
    }//deleteList
}