package com.nevermore.base.utils

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment

/**
 *
 * @author: xct
 * create on: 2022/8/23 18:09
 *
 */

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Float): Int = dipF(value).toInt()
fun Context.dipF(value: Float): Float = value * resources.displayMetrics.density

//return sp dimension value in pixels
fun Context.sp(value: Float): Int = spF(value).toInt()
fun Context.spF(value: Float): Float = value * resources.displayMetrics.scaledDensity

//converts px value into dip or sp
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)
fun Context.dimenF(@DimenRes resource: Int): Float = resources.getDimension(resource)

//the same for the views
fun View.dip(value: Float): Int = context.dip(value)
fun View.dipF(value: Float): Float = context.dipF(value)
fun View.sp(value: Float): Int = context.sp(value)
fun View.px2dip(px: Int): Float = context.px2dip(px)
fun View.px2sp(px: Int): Float = context.px2sp(px)
fun View.dimen(@DimenRes resource: Int): Int = context.dimen(resource)

fun Fragment.dip(value: Float): Int = requireActivity().dip(value)
fun Fragment.dipF(value: Float): Float = requireActivity().dipF(value)
fun Fragment.sp(value: Float): Int = requireActivity().sp(value)
fun Fragment.spF(value: Float): Float = requireActivity().spF(value)
fun Fragment.px2dip(px: Int): Float = requireActivity().px2dip(px)
fun Fragment.px2sp(px: Int): Float = requireActivity().px2sp(px)
fun Fragment.dimen(@DimenRes resource: Int): Int = requireActivity().dimen(resource)