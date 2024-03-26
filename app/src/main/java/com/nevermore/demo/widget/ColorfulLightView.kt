package com.nevermore.demo.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4

/**
 *
 * @author: xct
 * create on: 2022/8/17 18:46
 *
 */
class ColorfulLightView(context: Context, attrs: AttributeSet?) : View(context, attrs) {


    private val paint = Paint()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        val (alpha, red, green, blue) = Color.GREEN
        super.onSizeChanged(w, h, oldw, oldh)
        val shader = RadialGradient(
            w.div(2f),
            h.div(2f),
            w.div(3f),
            intArrayOf(Color.GREEN, Color.TRANSPARENT),
            null,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        setRenderEffect(effect)
        paint.setShadowLayer(50f, 0f, 0f, Color.BLUE)
        with(canvas) {
            drawCircle(width / 2f, height / 2f, 80f, paint)
        }
    }
}