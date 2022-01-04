package com.example.mylists

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.entities.MyList
import com.example.mylists.viewmodels.ItemViewModel
import com.example.mylists.viewmodels.ListViewModel

class ViewList : AppCompatActivity() {
    //item data
    private lateinit var listData: MyList
    private lateinit var listVM: ListViewModel
    private lateinit var itemVM: ItemViewModel
    private lateinit var checkAdapter: CheckListAdapter
    //views
    private lateinit var title: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var editBtn: Button
    private lateinit var shareBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)

        //view model
        itemVM = ItemViewModel(this.application)
        listVM = ListViewModel(this.application)

        //get list data
        val lid = intent.getLongExtra("list_id", 0)
        itemVM.listId = lid
        listData = listVM.getListById(lid)

        //set up adapter
        checkAdapter = CheckListAdapter()
        itemVM.getItems().observe(this, { item -> checkAdapter.addItem(item) })

        //views
        title = findViewById(R.id.list_title)
        recyclerView = findViewById(R.id.current_rv)
        editBtn = findViewById(R.id.edit_but)
        shareBtn = findViewById(R.id.share_btn)

        //set recycler view
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = checkAdapter

        title.text = listData.name

        //onClick
        editBtn.setOnClickListener{
            val editIntent = Intent(this, SetList::class.java)
            editIntent.putExtra("list_id", lid)
            startActivity(editIntent)
        }
        shareBtn.setOnClickListener{ println("SHARE") }
    }//onCreate

}//end class