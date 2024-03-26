package com.nevermore.base.adapter.factory

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nevermore.base.adapter.BaseRVAdapter
import com.nevermore.base.adapter.BindingViewHolder

object AdapterFactory {
    fun <T, VH : RVVH> createAdapter(adapterTypeItem: AdapterTypeItem<T, VH>) =
        object : BaseRVAdapter<T, VH>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
                return adapterTypeItem.create(parent, viewType, this)
            }

            override fun onBindViewHolder(holder: VH, position: Int) {
                adapterTypeItem.bind(holder, getItem(position), position, this)
            }

            override fun getItemViewType(position: Int): Int {
                return adapterTypeItem.getItemViewType(this, position)
            }
        }

    fun <T, VH : RVVH> createAdapter(
        vhCreator: IVHCreator<VH>,
        vhBinder: IVHBinder<T, VH>,
    ) = createAdapter(newItem(vhCreator, vhBinder) { _, _ -> true })

    inline fun <reified VB : ViewBinding, T> createAdapter(vhBinder: IVHBinder<T, BindingViewHolder<VB>>) =
        createAdapter(bindingVHCreator(), vhBinder)

    fun <T, VH : RVVH> buildAdapter(builder: MultiTypesItemBuilder<T, VH>.() -> Unit) =
        createAdapter(MultiTypeAdapterTypeItem(MultiTypesItemBuilder<T, VH>().apply(builder).build()))


}