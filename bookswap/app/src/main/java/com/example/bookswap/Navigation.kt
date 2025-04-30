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
                onLoginClick = { navController.navigate(Routes.Home.route) },
                onSignupClick = { navController.navigate(Routes.Signup.route) }
            )
        }
        composable(Routes.Signup.route) {
            SignupScreen(
                onSignupClick = { navController.navigate(Routes.Home.route) },
                onLoginClick = { navController.navigate(Routes.Login.route) }
            )
        }
        composable(Routes.Home.route) {
            HomeScreen(
                books = sampleBooks,
                onBookClick = { /* navigate to detail or show toast */ },
                onNavigateToMyBooks = { navController.navigate(Routes.MyBooks.route) },
                onNavigateToAddBook = { navController.navigate(Routes.AddBook.route) },
                onNavigateToProfile = { navController.navigate(Routes.Profile.route) }
            )
        }
        composable(Routes.MyBooks.route) {
            MyBooksScreen(
                myBooks = sampleBooks,
                onEditBook = { /* handle edit */ },
                onDeleteBook = { /* handle delete */ },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.AddBook.route) {
            AddBookScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.Profile.route) {
            ProfileScreen(onNavigateBack = { navController.popBackStack() })
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
