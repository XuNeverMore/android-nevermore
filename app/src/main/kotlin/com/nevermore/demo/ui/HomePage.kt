package com.nevermore.demo.ui

import android.content.Intent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.nevermore.demo.Screen
import com.nevermore.demo.SplashActivity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomePage(navCtrl: NavHostController) {
    val context = LocalContext.current
    FlowRow {
        OutlinedButton(onClick = {
            context.startActivity(Intent(context, SplashActivity::class.java))
        }) {
            Text(text = "Samples")
        }
        OutlinedButton(onClick = {
            navCtrl.navigate(Screen.Marquee.router)
        }) {
            Text(text = Screen.Marquee.title)
        }
    }
}
