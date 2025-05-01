package com.example.bookswap.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookswap.R
import com.example.bookswap.data.Book
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    onNavigateToMyBooks: () -> Unit,
    onNavigateToAddBook: () -> Unit,
    onNavigateToProfile: () -> Unit
) {

    val backgroundColor = Color(0xFFFBEDFF)
    val selectedNavBackground = Color(0xFF8F28C6)
    val navIconColor = Color.Black
    val navTextColor = Color.Black
    val bookBorderColor = Color(0xFF8F28C6)

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Network",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date()),
                color = Color.Black,
                style = MaterialTheme.typography.bodySmall
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "100%",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    imageVector = Icons.Default.BatteryFull,
                    contentDescription = "Battery",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }

        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search for books...") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, Color.LightGray, RectangleShape)
                .background(backgroundColor),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            shape = RectangleShape,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {})
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Book Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(books.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.author.contains(searchQuery, ignoreCase = true)
            }) { book ->
                BookItem(
                    book = book,
                    onClick = { onBookClick(book) },
                    backgroundColor = backgroundColor,
                    borderColor = bookBorderColor
                )
            }
        }

        // Bottom Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = true,
                onClick = { /* Already on home */ },
                selectedBackground = selectedNavBackground,
                backgroundColor = backgroundColor,
                iconColor = navIconColor,
                textColor = navTextColor
            )

            NavigationItem(
                icon = Icons.Outlined.Book,
                label = "My Book",
                isSelected = false,
                onClick = onNavigateToMyBooks,
                selectedBackground = selectedNavBackground,
                backgroundColor = backgroundColor,
                iconColor = navIconColor,
                textColor = navTextColor
            )

            NavigationItem(
                icon = Icons.Default.Add,
                label = "Add Book",
                isSelected = false,
                onClick = onNavigateToAddBook,
                selectedBackground = selectedNavBackground,
                backgroundColor = backgroundColor,
                iconColor = navIconColor,
                textColor = navTextColor
            )

            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = onNavigateToProfile,
                selectedBackground = selectedNavBackground,
                backgroundColor = backgroundColor,
                iconColor = navIconColor,
                textColor = navTextColor
            )
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit,
    backgroundColor: Color,
    borderColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RectangleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Column {
            AsyncImage(
                model = book.coverImage,
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = painterResource(id = R.drawable.book_placeholder),
                error = painterResource(id = R.drawable.book_placeholder)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )

            Text(
                text = "Owner: ${book.owner ?: "Unknown"}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedBackground: Color,
    backgroundColor: Color,
    iconColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .border(
                width = 1.dp,
                color = if (isSelected) selectedBackground else Color.LightGray,
                shape = RectangleShape
            )
            .background(if (isSelected) selectedBackground else backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.width(72.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = textColor,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}