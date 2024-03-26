package com.nevermore.base.adapter

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * @author chuanting
 * @date 2023/6/15 10:54
 *
 */
open class CommonItemDecoration(
    val left: Int = 0,
    val top: Int = 0,
    val right: Int = 0,
    val bottom: Int = 0,
    val itemDecor: (position: Int, itemCount: Int, rect: Rect) -> Boolean = { _, _, _ -> false }
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: parent.childCount
        if (itemDecor.invoke(childAdapterPosition, itemCount, outRect).not()) {
            outRect.set(left, top, right, bottom)
        }
    }
}

fun createDividerItemDecoration(context: Context, @ColorInt color: Int, height: Int, left: Int = 0, right: Int = 0) =
    DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
        val drawable = ShapeDrawable().apply {
            paint.color = color
            intrinsicHeight = height
        }
        if (left == 0 && right == 0) {
            setDrawable(drawable)
        } else {
            setDrawable(LayerDrawable(arrayOf(drawable)).also {
                it.setLayerInsetLeft(0, left)
                it.setLayerInsetRight(0, right)
            })
        }
    }


class AverageItemDecoration(val padding: Int) :
    CommonItemDecoration(padding, padding, padding, padding)