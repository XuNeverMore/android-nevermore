package com.nevermore.demo.fragments.permission

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.nevermore.base.utils.UtilPermission
import com.nevermore.demo.databinding.FragmentPermissionBinding


class PermissionFragment : Fragment() {
    private lateinit var viewBinding: FragmentPermissionBinding

    companion object {
        val necessaryPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
        )
    }

    private lateinit var resultLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var launcherAppSetting: ActivityResultLauncher<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcherAppSetting = registerForActivityResult(AppSettingContract()) {
            resultLauncher.launch(necessaryPermissions)
        }
        val ctx = requireContext()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.isNotEmpty() && it.values.all { result -> result }) {
                    Toast.makeText(ctx, "All permission Granted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val lackedPermissions =
                        UtilPermission.getLackedPermissions(ctx, necessaryPermissions.toList())
                    AlertDialog.Builder(ctx)
                        .setTitle("Permission Required.")
                        .setMessage(
                            """
                            Please agree the permissions:
                            $lackedPermissions
                            """
                        )
                        .setPositiveButton("ok") { _, _ ->
                            launcherAppSetting.launch(0)
                        }
                        .setNegativeButton("cancel", null)
                        .show()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPermissionBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnRequest.setOnClickListener {
            resultLauncher.launch(necessaryPermissions)
        }
    }
}