package com.nevermore.base.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.nevermore.base.ext.layoutInflater

/**
 *
 * @author xct
 * create on: 2023/2/20 18:00
 *
 */
class ArrayRVAdapter<T>(
    @LayoutRes val itemLayoutId: Int,
    @IdRes val itemTextViewId: Int,
    private val contentProvider: (T) -> CharSequence,
    val onItemClickListener: (position: Int, item: T) -> Unit
) :
    BaseRVAdapter<T, TextViewHolder>() {


    constructor(
        contentProvider: (T) -> CharSequence,
        onItemClickListener: (position: Int, item: T) -> Unit
    ) : this(
        android.R.layout.simple_list_item_1,
        android.R.id.text1,
        contentProvider,
        onItemClickListener
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val itemView = parent.context.layoutInflater.inflate(itemLayoutId, parent, false)
        return TextViewHolder(itemView, itemTextViewId)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item: T = getItem(position)
        holder.textView.text = contentProvider.invoke(item)
        holder.itemView.setOnClickListener {
            onItemClickListener(position, item)
        }
    }

}

class TextViewHolder(itemView: View, @IdRes val textViewId: Int) :
    RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(textViewId)
}