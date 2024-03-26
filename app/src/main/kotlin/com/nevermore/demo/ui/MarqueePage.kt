package com.nevermore.demo.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.LocaleConfig
import android.content.pm.ActivityInfo
import android.view.Window
import android.view.WindowManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.nevermore.demo.R

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MarqueePage(act: Activity) {
    val window = act.window
    val cl = WindowCompat.getInsetsController(window, window.decorView)
    DisposableEffect(Unit) {
        cl.hide(WindowInsetsCompat.Type.systemBars())
        act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            cl.show(WindowInsetsCompat.Type.systemBars())
            act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
    var text by remember { mutableStateOf("黄河之水天上来，奔流到海不复回。白日依山尽，黄河入海流。。。") }
    var editVisible by remember { mutableStateOf(false) }
    var textValue by remember {
        mutableStateOf(TextFieldValue())
    }
    if (editVisible) {
        AlertDialog(onDismissRequest = { editVisible = false }) {
            Card(modifier = Modifier.width(300.dp)) {
                Column(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "请输入：")
                    TextField(value = textValue, onValueChange = { textValue = it })
                    Button(onClick = {
                        editVisible = false
                        text = textValue.text
                    }) {
                        Text(text = "确定")
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable() {
                cl.hide(WindowInsetsCompat.Type.systemBars())
                editVisible = !editVisible
            },
    ) {

        Text(
            text = text,
            fontSize = 200.sp,
            modifier = Modifier
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    velocity = 320.dp
                )
                .align(Alignment.Center),
            style = TextStyle(
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}