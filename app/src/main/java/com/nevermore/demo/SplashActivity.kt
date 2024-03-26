package com.nevermore.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        printMsg()
    }

    private fun printMsg() {
        val stringExtra = intent.getStringExtra("seed_msg")
        Log.i(TAG, "printMsg: $stringExtra")
    }

}

private const val TAG = "SplashActivity"