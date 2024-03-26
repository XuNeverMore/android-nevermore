package com.nevermore.base.ext

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import androidx.core.content.ContextCompat

/**
 *
 * @author xct
 * create on: 2023/2/23 11:50
 *
 */
val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED