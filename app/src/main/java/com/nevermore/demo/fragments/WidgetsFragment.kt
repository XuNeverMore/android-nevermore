package com.nevermore.demo.fragments

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.nevermore.base.adapter.AverageItemDecoration
import com.nevermore.base.adapter.BaseRVAdapter
import com.nevermore.base.adapter.BindingViewHolder
import com.nevermore.base.adapter.CommonItemDecoration
import com.nevermore.base.utils.dip
import com.nevermore.demo.R
import com.nevermore.demo.databinding.FragmentWidgetsBinding
import com.nevermore.demo.databinding.ItemImgTxtBinding
import kotlin.random.Random


class WidgetsFragment : Fragment() {


    private lateinit var binding:FragmentWidgetsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWidgetsBinding.inflate(inflater)
            .also { binding = it }
            .root
    }
    class RandomColorGenerator {
        private val colors = listOf(
            "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF",
            "#FFA500", "#800080", "#008000", "#000080", "#FFC0CB", "#800000"
        )
        fun generateRandomColor(): String {
            val randomIndex = Random.nextInt(colors.size)
            return colors[randomIndex]
        }
    }
    data class WordColor(val word: String, val color: String)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ctx = view.context
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = FlexboxLayoutManager(ctx,FlexDirection.ROW)
        recyclerView.addItemDecoration(AverageItemDecoration(ctx.dip(6f)))
        val adapter = object :BaseRVAdapter<WordColor,BindingViewHolder<ItemImgTxtBinding>>(){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemImgTxtBinding> {
                return BindingViewHolder.create(parent)
            }

            override fun onBindViewHolder(holder: BindingViewHolder<ItemImgTxtBinding>, position: Int) {
                val item = getItem(position)
                val root = holder.viewBinding.root
                val layoutParams = root.layoutParams
                if (layoutParams is FlexboxLayoutManager.LayoutParams) {
                    layoutParams.flexGrow = 1f
                }
                holder.viewBinding.tv.text = item.word
                val radius = 30f
                val floatArray = FloatArray(8)
                repeat(floatArray.size){
                    floatArray[it] = radius
                }
                root.background = ShapeDrawable(RoundRectShape(floatArray,null,null)).also { it.paint.color = Color.parseColor(item.color) }
            }
        }

        val colorGenerator = RandomColorGenerator()
        val wordList = generateRandomWords(20)
        val wordColorList = mutableListOf<WordColor>()
        for (word in wordList) {
            val color = colorGenerator.generateRandomColor()
            val wordColor = WordColor(word, color)
            wordColorList.add(wordColor)
        }
        adapter.addAll(wordColorList)
        recyclerView.adapter = adapter

    }
    fun generateRandomWords(count: Int): List<String> {
        val wordList = mutableListOf<String>()
        val alphabet = ('a'..'z')
        repeat(count) {
            val wordLength = Random.nextInt(3, 8)
            val word = (1..wordLength)
                .map { alphabet.random() }
                .joinToString("")
            wordList.add(word)
        }
        return wordList
    }

}