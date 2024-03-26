package com.nevermore.demo.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.PathShape
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nevermore.demo.databinding.FragmentDrawablesBinding

class DrawablesFragment : Fragment() {

    private lateinit var binding: FragmentDrawablesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentDrawablesBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            btn.setOnClickListener {
                showOptionDialog()
            }
        }
    }

    private fun showOptionDialog() {

        AlertDialog.Builder(requireContext())
            .setItems(arrayOf("triangle0", "triangle1", "oval_border")) { p0, p1 ->

                when (p1) {
                    0 -> {
                        setTriangle(0)
                    }

                    1 -> setTriangle(1)
                    2 -> setOvalBorder()
                    else -> {}
                }


            }
            .show()
    }

    private fun setOvalBorder() {
        val shape = OvalShape()
        val drawable = ShapeDrawable(shape)
        val paint = drawable.paint
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        val padding = 5
        drawable.setPadding(padding, padding, padding, padding)
        drawable.intrinsicWidth = 50
        drawable.intrinsicHeight = 50
//        binding.image.scaleType = ImageView.ScaleType.CENTER
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.image.foreground = drawable
        }
    }

    private fun setTriangle(i: Int) {
        val path = android.graphics.Path()
        when (i) {
            0 -> {
                path.moveTo(0f, 0f)
                path.lineTo(100f, 100f)
                path.lineTo(0f, 100f)
            }

            1 -> {
                path.apply {
                    moveTo(50f, 0f)
                    lineTo(100f, 100f)
                    lineTo(0f, 100f)
                }
            }

            else -> {}
        }
        path.close()
        val shape = PathShape(path, 100f, 100f)
        val shapeDrawable = ShapeDrawable(shape)
        shapeDrawable.paint.color = Color.GREEN
        shapeDrawable.intrinsicWidth = 100
        shapeDrawable.intrinsicHeight = 100
        val image = binding.image
        image.setImageDrawable(shapeDrawable)
    }
}