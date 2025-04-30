package com.example.bookswap.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.example.bookswap.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookswap.data.Book
import java.text.SimpleDateFormat
import java.util.*

val BackgroundColor = Color(0xFFFBEDFF)
val SelectedNavBackground = Color(0xFF8F28C6) // Purple background for selected item
val UnselectedNavBackground = Color.White    // White background for unselected items
val NavIconColor = Color.Black               // Black icons for all states
val NavTextColor = Color.Black               // Black text for all items

@Composable
fun MyBooksScreen(
    myBooks: List<Book>,
    onEditBook: (Book) -> Unit,
    onDeleteBook: (Book) -> Unit,
    onNavigateBack: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onMyBooksClick: () -> Unit = {},
    onAddBookClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundColor)
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "My Books",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Book List
        if (myBooks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No books added yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(myBooks) { book ->
                    BookListItem(
                        book = book,
                        onEdit = { onEditBook(book) },
                        onDelete = { onDeleteBook(book) }
                    )
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                    )
                }
            }
        }

        // Bottom Navigation with Black Icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = false,
                onClick = onHomeClick
            )

            NavigationItem(
                icon = Icons.Outlined.Book,
                label = "My Book",
                isSelected = true,
                onClick = onMyBooksClick
            )

            NavigationItem(
                icon = Icons.Default.Add,
                label = "Add Book",
                isSelected = false,
                onClick = onAddBookClick
            )

            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = false,
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) SelectedNavBackground else UnselectedNavBackground)
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
                tint = NavIconColor, // Always black icon
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = NavTextColor, // Always black text
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BookListItem(
    book: Book,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Color(0xFF8F28C6),
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color(0xFFFBEDFF))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1
                )
                Text(
                    text = "Author : ${book.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Row(modifier = Modifier.padding(top = 12.dp)) {
                    OutlinedButton(
                        onClick = onEdit,
                        border = BorderStroke(1.dp, Color.Green),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Text("Edit", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = onDelete,
                        border = BorderStroke(1.dp, Color.Red),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                    ) {
                        Text("Delete", color = Color.Black)
                    }
                }
            }

            AsyncImage(
                model = book.coverImage,
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 100.dp, height = 140.dp)
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(id = R.drawable.book_placeholder),
                error = painterResource(id = R.drawable.book_placeholder)
            )
        }
    }
}