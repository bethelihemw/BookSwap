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
//            val navController = rememberNavController()
//            Description(navController)
//            AppNavigation(navController = navController)



//        val uiState = remember { mutableStateOf(LaunchUiState()) } // Example UI state
//            LaunchScreen(
//                uiState = uiState.value,
//                onStartClicked = {
//                    // Handle the start button click here
//                    println("Start button clicked!")
//                    uiState.value = uiState.value.copy(isReady = false, isLoading = true)
//                    // Simulate loading
//                    android.os.Handler().postDelayed({
//                        uiState.value = uiState.value.copy(isLoading = false, isReady = true)
//                    }, 2000)
//                }
//            )

//            ImageS()
            MainScreenWithBottomNav()




//            val navController = rememberNavController()
//            AppNavigation(navController = navController)






//            SignupScreen(
//                onSignupClick = {
//                    // Handle signup button click
//                    println("Signup button clicked!")
//                    // Add your signup logic here (e.g., navigation, API call)
//                },
//                onLoginClick = {
//                    // Handle login text click
//                    println("Login text clicked!")
//                    // Add your navigation to the login screen here
//                }
//            )


//            MyAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
        }
    }
}

//@Composable
//fun AppNavigation(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = "screenA") {
//        composable("screenA") { LaunchScreen(navController = navController) }
//        composable("screenB") { LoginScreen() }
//    }
//}