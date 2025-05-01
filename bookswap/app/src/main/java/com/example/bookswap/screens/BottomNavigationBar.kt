package com.example.bookswap.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFFF7DEFE))
            .padding(horizontal = 1.dp),

        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically

    ) {
        items.forEachIndexed { index, item ->
            BottomNavItem(
                item = item,
                isSelected = index == selectedItem,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color(0xFF9933CC).copy(alpha = 0.8f) else Color.Transparent) // Highlight selected
            .padding(vertical = 8.dp , horizontal = 30.dp)
//
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (isSelected) Color.White else Color.Black // White for selected
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            textAlign = TextAlign.Center,
            color = if (isSelected) Color.White else Color.Black,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun MainScreenWithBottomNav() {
    val items = listOf(
        BottomNavItem(title = "Home", icon = Icons.Filled.Home, route = "home"),
        BottomNavItem(title = "My Book", icon = Icons.Filled.List, route = "my_book"),
        BottomNavItem(title = "Add Book", icon = Icons.Filled.Add, route = "add_book"),
        BottomNavItem(title = "Profile", icon = Icons.Filled.Person, route = "profile")
    )

    var selectedIndex by remember { mutableStateOf(0) }
//    val selectedIndex = items.indexOf(selectedItem) // Get the index of the selected item

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedItem = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { paddingValues ->
        ContentScreens(
            modifier = Modifier.padding(paddingValues),
            selectedIndex = selectedIndex
        )
    }
}

@Composable
fun ContentScreens(modifier: Modifier = Modifier, selectedIndex: Int) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    { // Optional: Center content
        when (selectedIndex) {
            0 -> HomePage()
            1 -> MyBooksScreen()
            2 -> AddBook()
            3 -> ProfilePage()
            else -> Text("Unknown Screen") // Handle potential out-of-bounds
        }
    }
}

@Composable
fun PreviewBottomNavigationBar() {
    val items = listOf(
        BottomNavItem(title = "Home", icon = Icons.Filled.Home, route = "home"),
        BottomNavItem(title = "My Book", icon = Icons.Filled.List, route = "my_book"),
        BottomNavItem(title = "Add Book", icon = Icons.Filled.Add, route = "add_book"),
        BottomNavItem(title = "Profile", icon = Icons.Filled.Person, route = "profile")
    )
    var selectedIndex by remember { mutableStateOf(0) }
    BottomNavigationBar(
        items = items,
        selectedItem = selectedIndex,
        onItemSelected = { selectedIndex = it }
    )
}
