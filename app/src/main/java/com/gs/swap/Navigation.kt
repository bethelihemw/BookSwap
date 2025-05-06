package com.gs.swap

import android.R.attr.text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gs.swap.Navigation

object Navigation {
    const val CurrentPage = "current_page"
    const val HomePage = "next_page"
}

@Composable
fun NavigationSetup() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.CurrentPage) {
        composable(route = Navigation.CurrentPage) {
            LaunchScreen(navController = navController, route = "next_screen", text = "Start Swapping")
        }
        composable(route = Navigation.HomePage) {
            MainScreenWithBottomNav()
        }
    }
}
