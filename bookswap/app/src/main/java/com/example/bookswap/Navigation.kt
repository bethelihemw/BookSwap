package com.example.bookswap

import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookswap.data.sampleBooks
import com.example.bookswap.screens.*

@Composable
fun BookSwapApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.Home.route) {
                        launchSingleTop = true
                    }
                },
                onSignupClick = {
                    navController.navigate(Routes.Signup.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Signup.route) {
            SignupScreen(
                onSignupClick = {
                    navController.navigate(Routes.Home.route) {
                        launchSingleTop = true
                    }
                },
                onLoginClick = {
                    navController.navigate(Routes.Login.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen(
                books = sampleBooks,
                onBookClick = { /* TODO: Show book details */ },
                onNavigateToMyBooks = {
                    navController.navigate(Routes.MyBooks.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToAddBook = {
                    navController.navigate(Routes.AddBook.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Routes.Profile.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.MyBooks.route) {
            MyBooksScreen(
                myBooks = sampleBooks,
                onEditBook = { /* TODO: handle edit */ },
                onDeleteBook = { /* TODO: handle delete */ },
                onHomeClick = {
                    navController.navigate(Routes.Home.route) {
                        launchSingleTop = true
                    }
                },
                onMyBooksClick = {
                    // Already on MyBooks; do nothing or show a message
                },
                onAddBookClick = {
                    navController.navigate(Routes.AddBook.route) {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(Routes.Profile.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.AddBook.route) {
            AddBookScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

object Routes {
    val Login = ScreenRoute("login")
    val Signup = ScreenRoute("signup")
    val Home = ScreenRoute("home")
    val MyBooks = ScreenRoute("mybooks")
    val AddBook = ScreenRoute("addbook")
    val Profile = ScreenRoute("profile")

    data class ScreenRoute(val route: String)
}
