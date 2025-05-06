package com.gs.swap

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
        coverImage = ""
    ),
    Book(
        title = "Brave New World",
        author = "Aldous Huxley",
        owner = "Alice",
        coverImage = ""
    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""
    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""
    ),
    Book(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        owner = "Bob",
        coverImage = ""
    )
)
