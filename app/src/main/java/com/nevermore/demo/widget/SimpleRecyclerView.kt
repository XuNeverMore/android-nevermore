package com.nevermore.demo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author xct
 * create on: 2022/9/21 14:14
 *
 */

fun interface ItemDesc {
    fun get(position: Int, item: Any): String
}

fun interface OnItemClickListener {
    fun onItemClicked(position: Int, item: Any)
}

class SimpleRecyclerView(context: Context, attrs: AttributeSet? = null) :
    RecyclerView(context, attrs) {
    private val mList: MutableList<Any> = mutableListOf()


    var itemDesc: ItemDesc = ItemDesc { _, item -> item.toString() }

    var onItemClickListener = OnItemClickListener { _, item -> }

    private val mAdapter = MyAdapter()

    init {
        layoutManager = LinearLayoutManager(context)

        adapter = mAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<Any>) {
        mList.clear()
        mList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    inner class MyAdapter : RecyclerView.Adapter<VH>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.textView.text = itemDesc.get(position, mList[position])
            holder.itemView.setOnClickListener {
                val poi = holder.adapterPosition
                onItemClickListener.onItemClicked(poi,mList[poi])
            }
        }

        override fun getItemCount(): Int {
            return mList.size
        }
    }


    inner class VH(itemView: View) : ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }


}