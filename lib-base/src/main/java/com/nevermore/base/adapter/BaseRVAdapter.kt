package com.nevermore.base.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    private val list: MutableList<T> = mutableListOf()

    var tag: String = "BaseRVAdapter"

    fun remove(t: T) {
        val index = list.indexOf(t)
        if (index > -1) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun add(position: Int, t: T): Boolean {
        return if (position > list.size) {
            false
        } else {
            list.add(position, t)
            notifyItemInserted(position)
            true
        }
    }

    fun addFirst(t: T) = add(0, t)

    fun addLast(t: T) = add(itemCount, t)

    @SuppressLint("NotifyDataSetChanged")
    fun replaceAll(dataList: List<T>) {
        list.clear()
        list.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addAll(dataList: List<T>) {
        val start = list.size
        list.addAll(dataList)
        notifyItemRangeInserted(start, dataList.size)
    }

    fun clear() {
        val count = list.size
        list.clear()
        notifyItemRangeRemoved(0, count)
    }

    override fun getItemCount(): Int = list.size

    fun update(t: T) {
        val index = list.indexOf(t)
        if (index > -1) {
            list[index] = t
            notifyItemChanged(index)
        }
    }

    fun getItem(position: Int) = list[position]


    fun isValidIndex(position: Int) = position in list.indices

    inline fun <reified Z> isInstanceAt(position: Int): Boolean {
        return if (isValidIndex(position)) {
            getItem(position) is Z
        } else {
            false
        }
    }
}

inline fun <reified VH : RecyclerView.ViewHolder> createViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutResId: Int
): VH {
    val inflater = LayoutInflater.from(parent.context)
    val constructor = VH::class.java.getConstructor(View::class.java)
    val view = inflater.inflate(layoutResId, parent, false)
    return constructor.newInstance(view) as VH
}