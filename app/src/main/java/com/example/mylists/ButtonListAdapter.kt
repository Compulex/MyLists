package com.example.mylists

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mylists.entities.MyList

class ButtonListAdapter: RecyclerView.Adapter<ButtonListAdapter.ButtonViewHolder>() {
    private var buttons = mutableListOf<MyList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        return ButtonViewHolder.create(parent)
    }//onCreateViewHolder

    override fun getItemCount(): Int {
        return buttons.size
    }//getItemCount

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(buttons, position)
    }//onBindViewHolder

    class ButtonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val listBtn = itemView.findViewById<Button>(R.id.list_btn)

        fun bind(list: MutableList<MyList>, position: Int){
            val currList = list[position]

            listBtn.text = currList.name //set button name

            listBtn.setOnClickListener {
                val viewIntent = Intent(itemView.context, ViewList::class.java)
                viewIntent.putExtra("list_id", currList.id)
                itemView.context.startActivity(viewIntent)
            }
        }//bind

        companion object{
            fun create(parent: ViewGroup): ButtonViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_button, parent, false)
                return ButtonViewHolder(view)
            }//create
        }
    }//end inner class

    fun addList(list: MutableList<MyList>){
        buttons = list
        notifyDataSetChanged()
    }//addList

    fun getItemAtPosition(position: Int): MyList {
        return buttons[position]
    }//getItemAtPosition
}//end class