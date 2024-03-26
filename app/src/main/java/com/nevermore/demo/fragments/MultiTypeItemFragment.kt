package com.nevermore.demo.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nevermore.base.adapter.BindingViewHolder
import com.nevermore.base.adapter.factory.AdapterFactory
import com.nevermore.base.adapter.factory.newItem
import com.nevermore.base.adapter.factory.newItemWithVB
import com.nevermore.demo.R
import com.nevermore.demo.databinding.FragmentMultiTypeItemBinding
import com.nevermore.demo.databinding.ItemImgTxtBinding
import com.nevermore.demo.databinding.ItemTxtBinding


class MultiTypeItemFragment : Fragment() {

    private lateinit var binding: FragmentMultiTypeItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentMultiTypeItemBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    data class ImgTxt(val drawable: Int, val txt: String)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = view.context
        binding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)

            adapter = AdapterFactory.buildAdapter<Any, RecyclerView.ViewHolder> {

                addItem(
                    newItem<String, BindingViewHolder<ItemTxtBinding>>(
                        vhCreator = { parent, viewType, _ ->
                            BindingViewHolder.create<ItemTxtBinding>(parent)
                        },
                        vhBinder = { vh, item, position, adapter ->
                            vh.viewBinding.root.text = item
                        },
                        viewTypePredictor = { adapter, position ->
                            adapter.isInstanceAt<String>(position)
                        })
                )

                addItem(newItemWithVB<ItemTxtBinding, Int> { vh, item, _, _ ->
                    vh.viewBinding.root.setBackgroundColor(item)
                })

                addItem(newItemWithVB<ItemImgTxtBinding, ImgTxt> { vh, item, _, _ ->
                    vh.viewBinding.apply {
                        tv.text = item.txt
                        iv.setImageResource(item.drawable)
                    }
                })

            }.apply {
                tag = "multi adapter"
                replaceAll(buildList {
                    add(Color.RED)
                    add("55555555")
                    add(ImgTxt(R.drawable.ic_launcher_foreground, "android"))
                    add("44444441")
                    add(Color.BLACK)
                    add("3333333333")
                    add(ImgTxt(android.R.drawable.ic_delete, "delete"))
                    add(Color.BLUE)
                    add("22222222")
                })
            }
        }
    }
}