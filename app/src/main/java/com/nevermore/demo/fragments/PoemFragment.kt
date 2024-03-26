package com.nevermore.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nevermore.demo.databinding.FragmentPoemBinding

class PoemFragment : Fragment() {
    private lateinit var viewBinding: FragmentPoemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPoemBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }
}