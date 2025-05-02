package com.gs.swap



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun LaunchScreen(navController: NavHostController, route: String, text: String){
    Column (
        modifier = Modifier.height(750.dp)
            .background(Color(0xFFFCEEFF)) // light pink background
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.launch), // Load image from resources
            contentDescription = "My Example Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(90.dp).height(90.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "BookSwap",fontSize = 30.sp,fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Welcome to BookSwap: Exchange your favorite books easily!", color =Color.Gray)
        Spacer(modifier = Modifier.height(80.dp))


        Button(
            onClick = {
                navController.navigate(Navigation.HomePage)
            }
        ) {
            Text(text)
        }

    }
}

@Composable
fun YourScreenWithNavigationButton(navController: NavHostController) {
    LaunchScreen(navController = navController, route = "next_screen", text = "Start Swapping")
}