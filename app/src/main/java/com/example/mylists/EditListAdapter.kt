package com.example.mylists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.entities.Item

class EditListAdapter: RecyclerView.Adapter<EditListAdapter.EditViewHolder>() {
    private var items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditViewHolder {
        return EditViewHolder.create(parent)
    }//onCreateViewHolder

    override fun getItemCount(): Int {
        return items.size
    }//getItemCount

    override fun onBindViewHolder(holder: EditViewHolder, position: Int) {
        holder.bind(items, position)
    }//onBindViewHolder

    class EditViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val name = itemView.findViewById<TextView>(R.id.item_name)
        private val qt = itemView.findViewById<TextView>(R.id.item_qt)
        private val desc = itemView.findViewById<TextView>(R.id.item_desc)

        fun bind(itemList: MutableList<Item>, position: Int){
            val currItem = itemList[position]
            //set text views to the attributes
            name.text = currItem.name
            qt.text = currItem.quantity.toString()
            desc.text = currItem.specifics

        }//bind

        companion object{
            fun create(parent: ViewGroup): EditViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return EditViewHolder(view)
            }//create
        }
    }//end inner class

    fun addItem(list : MutableList<Item>){
        items = list
        notifyDataSetChanged()
    }//addItem

    fun getItemAtPosition(position: Int): Item{
        return items[position]
    }//getItemAtPosition
}