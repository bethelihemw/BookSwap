package com.gs.swap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.compose.rememberNavController
import com.gs.swap.ui.theme.SWAPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//
//            LoginScreen(
//                onLoginClick = {
//                    // Handle the "Log in" button click here
//                    println("Log in button clicked")
//                    // You would typically navigate to the home screen or perform login logic
//                },
//                onSignupClick = {
//                    // Handle the "Sign Up" button click here
//                    println("Sign Up button clicked")
//                    // You would typically navigate to the sign-up screen
//                }
//            )
            MainScreenWithBottomNav()
//            val navController = rememberNavController()
////                LaunchScreen(navController = navController, route = "next_screen", text = "Start Swapping")
//                Description(navController = navController)
////                MainScreenWithBottomNav()

        }
    }
}

