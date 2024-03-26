package com.nevermore.base.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.annotation.StyleRes
import androidx.viewbinding.ViewBinding
import com.nevermore.base.ext.createViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *
 * @author xuchuanting
 * create on 2021/8/31 16:27
 * description:
 *
 */
abstract class BaseViewBindingDialog(context: Context, @StyleRes style: Int) :
    Dialog(context, style) {

    abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}

inline fun <reified VB : ViewBinding> Dialog.viewBindingDelegate(): Lazy<VB> =
    lazy { createViewBinding(layoutInflater) }

