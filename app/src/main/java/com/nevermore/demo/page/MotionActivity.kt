package com.nevermore.demo.page

import android.os.Bundle
import com.nevermore.demo.base.BaseVBActivity
import com.nevermore.demo.base.viewBindingDelegate

import com.nevermore.demo.databinding.ActivityMotionBinding

class MotionActivity : BaseVBActivity() {
    override val viewBinding by viewBindingDelegate<ActivityMotionBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}