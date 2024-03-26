package com.nevermore.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nevermore.demo.ui.HomePage
import com.nevermore.demo.ui.MarqueePage
import com.nevermore.demo.ui.theme.XAndroidDemoTheme

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navCtrl = rememberNavController()
            XAndroidDemoTheme {
                NavHost(navController = navCtrl, startDestination = Screen.Home.router) {
                    val clz = Screen::class
                    val sealedSubclasses = clz.sealedSubclasses
                    sealedSubclasses.forEach {
                        Log.i(TAG, "screen sub: ${it.simpleName}")
                    }
                    composable(Screen.Home.router) {
                        HomePage(navCtrl)
                    }
                    composable(Screen.Marquee.router) {
                        MarqueePage(this@MainActivity)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XAndroidDemoTheme {
        Greeting("Android")
    }
}