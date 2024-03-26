package com.nevermore.demo.page

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nevermore.base.adapter.BaseRVAdapter
import com.nevermore.demo.R
import com.nevermore.demo.base.BaseVBActivity
import com.nevermore.demo.base.viewBindingDelegate
import com.nevermore.demo.databinding.ActivityFlipBinding
import java.lang.reflect.InvocationHandler

class FlipActivity : BaseVBActivity() {
    override val viewBinding by viewBindingDelegate<ActivityFlipBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val items = mutableListOf(
            "床前明月光",
            "疑是地上霜",
            "举头望明月",
            "低头思故乡"
        )
        val loopItem = mutableListOf<String>()
        loopItem.addAll(items)
        val viewPager2 = viewBinding.viewPager2
        if (items.size > 1) {
            loopItem.add(0, items[items.lastIndex])
            loopItem.add(items[0])
            val last = loopItem.lastIndex
            viewPager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            viewPager2.setCurrentItem(last - 1, false)
                        }
                        last -> {
                            viewPager2.setCurrentItem(1, false)
                        }
                        else -> {}
                    }
                }
            })
        }
        viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = object : BaseRVAdapter<String, SimpleViewHolder>() {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): SimpleViewHolder =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_flip, parent, false)
                        .let {
                            SimpleViewHolder(it)
                        }

                override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                    holder.textView.text = getItem(position)
                }
            }.apply {
                addAll(loopItem)
            }
            setCurrentItem(1, false)
        }

        changeSpeed(viewPager2)
        startLoop()
    }

    /**
     * 修改滚动速度
     */
    private fun changeSpeed(viewPager2: ViewPager2) {

        // TODO: 获取内部recyclerView的layoutManager修改smoothScrollToPosition方法


    }

    private val handler = Handler(Looper.getMainLooper())
    private fun startLoop() {
        val vp = viewBinding.viewPager2
        val interval = 2000L
        val runnable = object : Runnable {
            override fun run() {
                val currentItem = vp.currentItem
                vp.setCurrentItem(currentItem + 1, true)
                handler.postDelayed(this, interval)
            }
        }
        handler.postDelayed(runnable, interval)
    }


    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView as TextView
    }
}