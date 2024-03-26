package com.nevermore.demo.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nevermore.base.ext.createViewBinding

/**
 *
 * @author xuchuanting
 * create on 2021/9/2 13:46
 * description:
 *
 */
abstract class BaseVBActivity: AppCompatActivity() {

    abstract val viewBinding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

}

inline fun <reified VB : ViewBinding> ComponentActivity.viewBindingDelegate(): Lazy<VB> =
    lazy { createViewBinding(layoutInflater) }