package com.nevermore.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 *
 * @author xuchuanting
 * create on 2021/10/9 10:29
 * description:
 *
 */
open class BindingViewHolder<VB : ViewBinding>(val viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {

        inline fun <reified T : ViewBinding> create(parent: ViewGroup): BindingViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val method = T::class.java.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
            val binding = method.invoke(null, inflater, parent, false) as T
            return BindingViewHolder(binding)
        }

    }

}