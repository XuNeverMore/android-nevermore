package com.nevermore.base.adapter.factory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.nevermore.base.adapter.BindingViewHolder

abstract class BindingVHAdapterTypeItem<VB : ViewBinding, T>(
    val viewTypePredictor: (XBaseAdapter, Int) -> Boolean,
    val vhCreator: IVHCreator<BindingViewHolder<VB>>
) :
    AdapterTypeItem<T, BindingViewHolder<VB>> {

    override fun create(parent: ViewGroup, viewType: Int, adapter: XBaseAdapter): BindingViewHolder<VB> {
        return vhCreator.create(parent, viewType, adapter)
    }

    override fun getItemViewType(adapter: XBaseAdapter, position: Int): Int {
        return if (viewTypePredictor(adapter, position)) {
            0
        } else {
            RecyclerView.INVALID_TYPE
        }
    }
}