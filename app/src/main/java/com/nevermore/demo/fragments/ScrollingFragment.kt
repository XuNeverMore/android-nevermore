package com.nevermore.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.nevermore.demo.databinding.FragmentScrollingBinding

/**
 *
 * @author: xct
 * create on: 2022/8/24 10:13
 *
 */
class ScrollingFragment:Fragment() {

    private lateinit var viewBinding: FragmentScrollingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentScrollingBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.toolbarLayout.title = "协调布局"
        viewBinding.fab.setOnClickListener { v ->
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}