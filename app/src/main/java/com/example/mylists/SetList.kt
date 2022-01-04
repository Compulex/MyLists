package com.example.mylists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.database.ListDatabase
import com.example.mylists.entities.Item
import com.example.mylists.entities.MyList
import com.example.mylists.viewmodels.ItemViewModel
import com.example.mylists.viewmodels.ListViewModel

class SetList : AppCompatActivity() {
    //data & view model
    private lateinit var listData: MyList
    private lateinit var itemData: Item
    private lateinit var listVM: ListViewModel
    private lateinit var itemVM: ItemViewModel
    private lateinit var elAdapter: EditListAdapter
    //views
    private lateinit var listName: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemName: EditText
    private lateinit var quantity: EditText
    private lateinit var specifics: EditText
    private lateinit var addBtn: Button
    private lateinit var doneBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_list)

        //instantiate views
        listName = findViewById(R.id.list_name)
        recyclerView = findViewById(R.id.edit_rv)
        itemName = findViewById(R.id.item_et)
        quantity = findViewById(R.id.quantity_et)
        specifics = findViewById(R.id.specifics_et)
        addBtn = findViewById(R.id.add_item_btn)
        doneBtn = findViewById(R.id.done_btn)

        //instantiate viewModels
        listVM = ListViewModel(this.application)
        itemVM = ItemViewModel(this.application)

        //get list object by id
        var id = intent.getLongExtra("list_id", 0)

        if(id == 0L){
            //get last list of lists
            val lol = ListDatabase.getInstance(this.application).listDao().getAllLists()
            id = lol[lol.size-1].id
        }
        listData = listVM.getListById(id)

        //set the name of the current list
        listName.setText(listData.name)

        //set recycler view and adapter
        elAdapter = EditListAdapter()
        itemVM.listId = id
        itemVM.getItems().observe(this, { item -> elAdapter.addItem(item) })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = elAdapter

        listName.doAfterTextChanged {
            //update list name - only if not empty
            if(listName.text.toString() != ""){
                listData.name = listName.text.toString()
                listVM.updateList(listData)
            }
        }//doAfterTextChanged

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
                    val item = elAdapter.getItemAtPosition(pos)

                    itemVM.deleteItem(item)
                }//onSwiped
            }
        )

        val edit = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }//onMove

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    itemData = elAdapter.getItemAtPosition(pos)

                    itemName.setText(itemData.name)
                    quantity.setText(itemData.quantity.toString())
                    specifics.setText(itemData.specifics)

                    "Update".also { addBtn.text = it }
                }//onSwiped
            }
        )

        //attach to recycler view
        delete.attachToRecyclerView(recyclerView)
        edit.attachToRecyclerView(recyclerView)

        //onClicks
        addBtn.setOnClickListener { setItem() }

        doneBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }//onCreate

    private fun setItem(){
        //add new item
        if(addBtn.text == "Add"){
            //get all info for item
            itemData = Item(itemName.text.toString(),
                quantity.text.toString().toInt(),
                specifics.text.toString(),
                false, listData.id)
            itemVM.insertItem(itemData) //add item to list
            Toast.makeText(applicationContext, "Item Added!", Toast.LENGTH_SHORT).show()
        }
        //update item
        else{
            itemData.name = itemName.text.toString()
            itemData.quantity = quantity.text.toString().toInt()
            itemData.specifics = specifics.text.toString()
            itemVM.updateItem(itemData)
            Toast.makeText(applicationContext, "Item Updated!", Toast.LENGTH_SHORT).show()

            "Add".also { addBtn.text = it }
        }

        //clear inputs after item is added
        itemName.setText("")
        quantity.setText("")
        specifics.setText("")
    }//setItem
}//end class