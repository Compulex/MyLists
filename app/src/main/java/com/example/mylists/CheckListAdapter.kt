package com.example.mylists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.entities.Item

class CheckListAdapter: RecyclerView.Adapter<CheckListAdapter.CheckViewHolder>() {
    private var fullList = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        return CheckViewHolder.create(parent)
    }//onCreateViewHolder

    override fun getItemCount(): Int {
        return fullList.size
    }//getItemCount

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        holder.bind(fullList, position)
    }//onBindViewHolder

    class CheckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val cb = itemView.findViewById<CheckBox>(R.id.checkbox)
        private val qt = itemView.findViewById<TextView>(R.id.check_qt)
        private val desc = itemView.findViewById<EditText>(R.id.check_desc)

        fun bind(items: MutableList<Item>, position: Int){
            val checkItem = items[position]

            //set views
            cb.text = checkItem.name
            qt.text = checkItem.quantity.toString()
            desc.setText(checkItem.specifics)

            cb.setOnClickListener{ checkItem.isChecked = true }
        }//bind

        companion object{
            fun create(parent: ViewGroup): CheckViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.checklist_item, parent, false)
                return CheckViewHolder(view)
            }//create
        }
    }//end inner class

    fun addItem(list : MutableList<Item>){
        fullList = list
        notifyDataSetChanged()
    }//addItem
}