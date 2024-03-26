package com.nevermore.demo.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.nevermore.base.utils.UtilStatusBar
import com.nevermore.demo.databinding.FragmentStatusBarBinding

/**
 *
 * @author: xct
 * create on: 2022/8/23 18:57
 *
 */
class StatusBarFragment : Fragment() {

    private lateinit var viewBinding: FragmentStatusBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentStatusBarBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            val window = requireActivity().window
            btnWhite.setOnClickListener {
                UtilStatusBar.setLightStatusBar(requireActivity(), true)
                window.statusBarColor = Color.WHITE
                UtilStatusBar.setNavigationBarColor(window, Color.WHITE)
            }
            btnBlack.setOnClickListener {
                val color = Color.BLACK
                UtilStatusBar.setLightStatusBar(requireActivity(), false)
                window.statusBarColor = color
                UtilStatusBar.setNavigationBarColor(window, color)
            }
            var show = true
            btnThrough.setOnClickListener {

                UtilStatusBar.setFullScreenMode(window, show.also { show = show.not() })
            }

            var decor = true
            btnInset.setOnClickListener {
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
                UtilStatusBar.setImmersiveMode(window,decor)
                decor = decor.not()
            }

            val insetsController = WindowCompat.getInsetsController(window, window.decorView)
            insetsController.addOnControllableInsetsChangedListener { controller, typeMask ->
                Log.i(TAG, "Changed: $typeMask")
            }
        }
    }
}

private const val TAG = "StatusBarFragment"