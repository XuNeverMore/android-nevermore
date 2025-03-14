package com.nevermore.demo.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nevermore.demo.Screen
import com.nevermore.demo.SplashActivity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomePage(navCtrl: NavHostController) {
    val context = LocalContext.current
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
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
