package com.gs.swap


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigation(modifier: Modifier = Modifier){
    val navItemList  = listOf(
        NavItems("Home", Icons.Default.Home) ,
        NavItems("MyBooks", Icons.Default.DateRange),
        NavItems("Add", Icons.Default.Add),
        NavItems("Profile", Icons.Default.Person))
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItems ->
                    NavigationRailItem(selected = selectedIndex == index , onClick = { selectedIndex = index }, icon = {
                        Icon(imageVector = navItems.icon , contentDescription = "icon")},
                        label = {
                            Text(text = navItems.label)})} }
        })
    { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
    } }
@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex : Int){
    when (selectedIndex){
        0-> HomePage()
//        1 -> MyBooks()
        2 -> AddBook()
        3 -> ProfilePage()
    }
}