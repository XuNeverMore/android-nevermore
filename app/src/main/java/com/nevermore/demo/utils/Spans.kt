package com.nevermore.demo.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt

class OutlineTextSpan(
    private val innerStrokeWidth: Float,
    private val outerStrokeWidth: Float,
    @ColorInt private val outlineStrokeColor: Int,
    @ColorInt private val innerTextColor: Int,
) :
    ReplacementSpan() {

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return paint.measureText(text, start, end).toInt()
    }


    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        text ?: return

        //outer
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = (outerStrokeWidth + innerStrokeWidth)
        paint.color = outlineStrokeColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        //inner
        paint.strokeWidth = innerStrokeWidth
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = innerTextColor
        paint.strokeWidth = (outerStrokeWidth /*+ innerStrokeWidth*/)
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

    }
}