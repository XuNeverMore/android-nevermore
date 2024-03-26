package com.nevermore.demo.fragments.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract

/**
 *
 * @author xct
 * create on: 2023/4/7 14:58
 *
 */
class AppSettingContract : ActivityResultContract<Int, Boolean>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(
                    Uri.fromParts(
                        "package",
                        context.packageName,
                        null
                    )
                )
        if (input != 0) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}