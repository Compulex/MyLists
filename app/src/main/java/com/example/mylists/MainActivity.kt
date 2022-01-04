package com.example.mylists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.entities.MyList
import com.example.mylists.viewmodels.ItemViewModel
import com.example.mylists.viewmodels.ListViewModel

class MainActivity : AppCompatActivity() {
    //list data and viewModel
    private lateinit var listData: MyList
    private lateinit var listVM: ListViewModel
    private lateinit var btnAdapter: ButtonListAdapter
    //views
    private lateinit var listRV: RecyclerView
    private lateinit var listName: EditText
    private lateinit var newBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewModel
        listVM = ListViewModel(this.application)
        val itemVM = ItemViewModel(this.application)

        //views
        listRV = findViewById(R.id.lists_rv)
        listName = findViewById(R.id.list_et)
        newBtn = findViewById(R.id.new_btn)

        //set list in recycler view
        btnAdapter = ButtonListAdapter()
        listVM.getLists().observe(this, { list -> btnAdapter.addList(list) })
        listRV.setHasFixedSize(true)
        listRV.layoutManager = LinearLayoutManager(this)
        listRV.adapter = btnAdapter

        //swipe to delete
        val delete = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }//onMove

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    val listBtn = btnAdapter.getItemAtPosition(pos)

                    listVM.deleteList(listBtn)

                    //delete all items along with it
                    itemVM.listId = listBtn.id
                    val itemList = itemVM.getItems()
                    for(item in itemList.value!!){
                        itemVM.deleteItem(item)
                    }
                    println("ALL DELETED: ${itemList.value?.size}")
                }//onSwiped
            }
        )
        delete.attachToRecyclerView(listRV)

        //has to have something in the text in order to set list
        newBtn.setOnClickListener {
            if(listName.text.toString() == ""){
                Toast.makeText(applicationContext, "Please fill in", Toast.LENGTH_SHORT).show()
            }
            else{
                listData = MyList(listName.text.toString())
                listVM.insertList(listData)

                //send id to Set activity
                val setIntent = Intent(this, SetList::class.java)
                setIntent.putExtra("list_id", 0L)
                startActivity(setIntent)
            }
        }//onClick
    }//onCreate
}