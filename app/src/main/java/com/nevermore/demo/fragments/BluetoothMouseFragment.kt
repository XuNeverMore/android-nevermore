package com.nevermore.demo.fragments

import android.Manifest
import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.nevermore.demo.R
import com.nevermore.demo.core.bluetooth.BluetoothHIDController
import com.nevermore.demo.core.bluetooth.CustomMotionListener
import com.nevermore.demo.databinding.FragmentBluetoothMouseBinding


class BluetoothMouseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentBluetoothMouseBinding
    private val ctrl: BluetoothHIDController? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BluetoothHIDController(requireContext())
        } else {
            null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBluetoothMouseBinding.inflate(inflater, container, false)
        return binding.root
    }

    inner class DiscoveryContract : ActivityResultContract<Unit, Boolean>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)


        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == Activity.RESULT_OK
        }
    }

    lateinit var resultLauncher: ActivityResultLauncher<Unit>
    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultLauncher = registerForActivityResult(DiscoveryContract()) {
            Log.i(TAG, "on discovery result: $it")

        }
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.values.all { it }) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ctrl?.startService()
                }
            } else {
                Log.i(TAG, "permission denied")
            }
        }.launch(permissions)
        binding.btnOpenDiscovery.setOnClickListener {
            resultLauncher.launch(Unit)
        }

        with(binding) {
            ctrl?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    tvMouse.setOnTouchListener(CustomMotionListener(requireContext(), ctrl))
                }
            }
        }
    }

    companion object {
        private const val TAG = "BluetoothMouseFragment"
    }

}