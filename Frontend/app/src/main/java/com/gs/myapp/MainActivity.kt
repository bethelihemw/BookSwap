package com.gs.myapp

//import ImageS
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gs.myapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("launch") {
            LaunchScreen(
                uiState = LaunchUiState(),
                onStartClicked = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignupScreen(
                onSignupClick = { /* TODO: Handle signup logic and navigate */ },
                onLoginClick = { navController.navigate("launch") }
            )
        }
        composable("home") {
            HomePage(navController = navController) // Pass navController here
        }
        composable("description") {
            Description(navController = navController)
        }
        // Add other composable destinations here
    }
}
