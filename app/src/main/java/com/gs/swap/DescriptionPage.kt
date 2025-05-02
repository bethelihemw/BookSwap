package com.gs.swap


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gs.swap.ui.theme.color1
import com.gs.swap.ui.theme.color2

@Composable
fun Description(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Spacer(modifier = Modifier.width(100.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, // Align icon and text vertically in the center
            horizontalArrangement = Arrangement.Start // Arrange items from the start (left)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow",
                tint = Color.Blue,
                modifier = Modifier.size(24.dp)
                    .clickable {
                        navController.navigate(Navigation.HomePage)
                    }
            )
            Spacer(modifier = Modifier.width(140.dp))
            Text(text = "Book Detail", fontSize = 22.sp,fontWeight = FontWeight.SemiBold)
        }

        Image(
            painter = painterResource(id = R.drawable.novel1), // Load image from resources
            contentDescription = "My Example Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(190.dp).height(280.dp)
        )
        Text(text = "The Lord Of The Rings",fontSize = 18.sp,fontWeight = FontWeight.SemiBold)
        Text(text = "Owner: J.R.R Tolkien ", color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "A hobbit named Frodo inherits the One Ring, which can destroy the " +
                "entire world. With the recently reawakened evil, being Sauron, going" +
                " after the Ring to cement his reign," +
                " Frodo joins with eight others to destroy the Ring and defeat Sauron.")
        Spacer(modifier = Modifier.width(28.dp))
        GradientButton(
            text = "Request Swap",
            textColor = Color.White,
            gradient = Brush.horizontalGradient(
                colors = listOf(
                    color1,
                    color2
                )
            )){}

    }
}
