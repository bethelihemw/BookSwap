package com.example.bookswap.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookswap.R
import com.example.bookswap.data.Book

@Composable
fun MyBooksScreen(
    myBooks: List<Book>,
    onEditBook: (Book) -> Unit,
    onDeleteBook: (Book) -> Unit,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text(
                text = "My Books",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)

            )
        }

        // Book list matching your exact design
        LazyColumn {
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
}

@Composable
fun BookListItem(
    book: Book,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Text content and action buttons
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Action buttons
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable(onClick = onEdit)
                        .padding(end = 16.dp)
                )
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable(onClick = onDelete)
                )
            }
        }

        // Right side: Book cover image
        Image(
            painter = painterResource(id = R.drawable.book_placeholder), // Replace with your image
            contentDescription = "Book cover",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBooksScreenPreview() {
    MaterialTheme {
        MyBooksScreen(
            myBooks = listOf(
                Book("1", "The Great Gatsby", "F. Scott Fitzgerald"),
                Book("2", "Atomic Habits", "James Clear"),
                Book("3", "The Silent Patient", "Alex Michaelides"),
                Book("1", "The Great Gatsby", "F. Scott Fitzgerald"),
                Book("2", "Atomic Habits", "James Clear"),
                Book("3", "The Silent Patient", "Alex Michaelides")
            ),
            onEditBook = {},
            onDeleteBook = {},
            onNavigateBack = {}
        )
    }
}