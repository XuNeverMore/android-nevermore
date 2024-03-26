package com.nevermore.demo

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.nevermore.demo.ui.HomePage
import com.nevermore.demo.ui.MarqueePage

sealed class Screen(val router: String, val title: String) {
    @Composable
    abstract fun Content(navController: NavHostController)

    data object Home : Screen("home", "Home") {
        @Composable
        override fun Content(navController: NavHostController) {
            HomePage(navCtrl = navController)
        }
    }

    data object Marquee : Screen("page_marquee", "marquee") {
        @Composable
        override fun Content(navController: NavHostController) {
        }
    }
}
