package com.gs.swap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BookItem(
    val title: String,
    val owner: String,
    val imageResId: Int
)

@Composable
fun HomePage() {
    val books = listOf(
        BookItem("I am watching you", "Alice Johnson", R.drawable.images2), // Replace with your image resources
        BookItem("1984", "Asterix", R.drawable.images2),
        BookItem("Pride and Prejudice", "Alice Johnson", R.drawable.images3),
        BookItem("The Great Gatsby", "Natneal Edale", R.drawable.images2),
        BookItem("The Great Gatsby", "Natneal Edale", R.drawable.images2),
        BookItem("1984", "Asterix", R.drawable.images2),
        BookItem("Pride and Prejudice", "Alice Johnson", R.drawable.images3),
        BookItem("The Great Gatsby", "Natneal Edale", R.drawable.images2),
        // Add more books here
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFCEEFF)) // Light pink background
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Home", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { /* TODO: Profile action */ }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.Black)
            }
        }

        // Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = {},
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            placeholder = { Text("Search for books...") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Book Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(books.size) { index ->
                BookCard(book = books[index])
            }
        }
    }
}

@Composable
fun BookCard(book: BookItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f), // Adjust aspect ratio to match the image
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = book.imageResId),
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("Owner: ${book.owner}", color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomePage()
}


