package com.gs.myapp


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PlayArrow
//import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
//import coil.compose.AsyncImage
//import com.example.bookswap.R
//import com.example.bookswap.data.Book
import java.text.SimpleDateFormat
import java.util.*


import java.util.UUID

data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val owner: String = "",
    val coverImage: String = ""
)

val sampleBooks = listOf(
    Book(
        title = "1984",
        author = "George Orwell",
        owner = "John",
        coverImage = ""
    ),
    Book(
        title = "Brave New World",
        author = "Aldous Huxley",
        owner = "Alice",
        coverImage = ""    ),
    Book(
        title = "Brave New World",
        author = "Aldous Huxley",
        owner = "Alice",
        coverImage = ""    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""    )
)




val BackgroundColor = Color(0xFFFBEDFF)
val SelectedNavBackground = Color(0xFF8F28C6)
val NavIconColor = Color.Black
val NavTextColor = Color.Black

@Composable
fun MyBooksScreen(
    myBooks: List<Book>,
    onEditBook: (Book) -> Unit,
    onDeleteBook: (Book) -> Unit,
    onHomeClick: () -> Unit,
    onMyBooksClick: () -> Unit,
    onAddBookClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        // Top Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(Icons.Default.Home, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
//            Text(
//                text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date()),
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.Black
//            )
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text("100%", style = MaterialTheme.typography.bodySmall, color = Color.Black)
//                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
//            }
        }

        // Header
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("My Books", style = MaterialTheme.typography.headlineMedium)
        }

        // Book List
        if (myBooks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }

        // Bottom Navigation
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            NavigationItem(Icons.Default.Home, "Home", false, onHomeClick)
//            NavigationItem(Icons.Outlined.PlayArrow, "My Book", true, onMyBooksClick)
//            NavigationItem(Icons.Default.Add, "Add Book", false, onAddBookClick)
//            NavigationItem(Icons.Default.Person, "Profile", false, onProfileClick)
//        }
    }
}

@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .border(1.dp, if (isSelected) SelectedNavBackground else Color.LightGray)
            .background(if (isSelected) SelectedNavBackground else BackgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.width(72.dp)
        ) {
            Icon(icon, contentDescription = label, tint = NavIconColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, color = NavTextColor, textAlign = TextAlign.Center, style = MaterialTheme.typography.labelSmall)
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
            .clip(RectangleShape)
            .border(1.dp, Color(0xFF8F28C6), RectangleShape)
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(book.title, style = MaterialTheme.typography.titleLarge, maxLines = 1)
                Text("Author: ${book.author}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 4.dp))

                Row(modifier = Modifier.padding(top = 12.dp)) {
                    OutlinedButton(
                        onClick = onEdit,
                        border = BorderStroke(1.dp, Color.Green),
                        shape = RectangleShape,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = BackgroundColor)
                    ) {
                        Text("Edit", color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedButton(
                        onClick = onDelete,
                        border = BorderStroke(1.dp, Color.Red),
                        shape = RectangleShape,
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = BackgroundColor)
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
                    .clip(RectangleShape),
                placeholder = painterResource(id = R.drawable.flower1),
                error = painterResource(id = R.drawable.flower2)
            )
        }
    }
}

@Composable
fun mybooks(){
    val myBooks = remember {
        mutableStateListOf(
            Book("1", "The Lord of the Rings", "J.R.R. Tolkien"),
            Book("2", "Pride and Prejudice", "Jane Austen"),
            Book("3", "1984", "George Orwell")
        )
    }

    MyBooksScreen(
        myBooks = myBooks,
        onEditBook = { book ->
            // Handle the edit book action
            println("Edit book: ${book.title}")
            // Implement your edit book logic (e.g., navigate to edit screen)
        },
        onDeleteBook = { book ->
            // Handle the delete book action
            println("Delete book: ${book.title}")
            myBooks.remove(book) // Example: remove from the list
            // Implement your delete book logic (e.g., update database)
        },
        onHomeClick = {
            // Handle home button click
            println("Home clicked!")
            // Implement navigation to the home screen
        },
        onMyBooksClick = {
            // Handle my books button click (you might already be here)
            println("My Books clicked!")
            // Implement any specific logic for the my books screen
        },
        onAddBookClick = {
            // Handle add book button click
            println("Add book clicked!")
            // Implement navigation to the add book screen
        },
        onProfileClick = {
            // Handle profile button click
            println("Profile clicked!")
            // Implement navigation to the profile screen
        }
    )

}

