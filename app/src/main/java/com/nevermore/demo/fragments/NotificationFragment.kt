package com.nevermore.demo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nevermore.demo.R
import com.nevermore.demo.core.NotificationUtil
import com.nevermore.demo.databinding.FragmentNotificationBinding


class NotificationFragment : Fragment() {
    private lateinit var viewBinding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentNotificationBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }

    private var count = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        NotificationUtil.deleteChannels(context)
        viewBinding.apply {
            btnSend.setOnClickListener {
                NotificationUtil.showDevice(context, "seed-$count", "mac:$count")
                count++
            }

            btnNotify.setOnClickListener {
                NotificationUtil.showNotify(context, "title-$count", "content:$count")
                count++
            }
        }
    }
}