package com.gs.myapp//package com.gs.myapp
//
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//
////
////
////
//
//
//
//@Composable
//fun SignupScreen(
//    onSignupClick: () -> Unit,
////    onLoginClick: () -> Unit,
//    navController: NavHostController
//){
//    TextButton(onClick = { navController.navigate("login") }) { // Navigate on click
//        Text(
//            "Already have an account? Log in",
//            color = Color.Black
//        )
//    }
//}
//
//
//
//@Composable
//fun AppNavigation(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = "signup") { // Set signup as start
//        composable("signup") {
//            SignupScreen(
//                onSignupClick = {
//                    // Handle signup logic
//                    println("Signup Clicked")
//                },
//                navController = navController // Pass the NavController
//            )
//        }
//        composable("login") {
//            LoginScreen()  // Your LoginPage composable
//        }
//        // Add other routes here
//    }
//}
//
////@Composable
////fun LoginPage() {
////    // Your composable for the login screen
////    androidx.compose.material3.Text(text = "This is the Login Page")
////}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////import android.net.Uri
////import androidx.activity.compose.rememberLauncherForActivityResult
////import androidx.activity.result.contract.ActivityResultContracts
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.border
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.Box
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.padding
////import androidx.compose.foundation.layout.size
////import androidx.compose.foundation.shape.CircleShape
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.Add
////import androidx.compose.material3.Icon
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.saveable.rememberSaveable
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.unit.dp
////import coil3.compose.rememberAsyncImagePainter
////import com.gs.myapp.R
////import kotlin.text.ifEmpty
////
////@Composable
////fun ImageS() {
////    val imageUri = rememberSaveable { mutableStateOf("") }
////    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.flower1 })
////    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
////        uri?.let { imageUri.value = it.toString() }
////    }
////
////    Box(
////        modifier = Modifier
////            .fillMaxSize() // Make the outer Box take up the whole screen
////            .padding(top = 50.dp), // Add some top padding to move it down from the very top
////        contentAlignment = Alignment.TopCenter // Align content to the top center
////    ) {
////        Box(contentAlignment = Alignment.Center) {
////            Image(
////                painter = painter,
////                contentDescription = "Book Cover",
////                contentScale = ContentScale.Crop,
////                modifier = Modifier
////                    .size(100.dp)
////                    .border(1.dp, Color.White)
////            )
////            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
////                Icon(
////                    imageVector = Icons.Filled.Add,
////                    contentDescription = "add Icon",
////                    tint = Color.Black,
////                    modifier = Modifier
////                        .size(34.dp)
////                        .clip(CircleShape)
////                        .clickable { launcher.launch("image/*") }
////                )
////            }
////        }
////    }
////}
////
////
////
////
//////import android.net.Uri
//////import androidx.activity.compose.rememberLauncherForActivityResult
//////import androidx.activity.result.contract.ActivityResultContracts
//////import androidx.compose.foundation.Image
//////import androidx.compose.foundation.border
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.Box
//////import androidx.compose.foundation.layout.size
//////import androidx.compose.foundation.shape.CircleShape
//////import androidx.compose.material.icons.Icons
//////import androidx.compose.material.icons.filled.Add
//////import androidx.compose.material3.Icon
//////import androidx.compose.runtime.Composable
//////import androidx.compose.runtime.mutableStateOf
//////import androidx.compose.runtime.saveable.rememberSaveable
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.clip
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.layout.ContentScale
//////import androidx.compose.ui.unit.dp
//////import coil3.compose.rememberAsyncImagePainter
//////import com.gs.myapp.R
//////import kotlin.text.ifEmpty
//////
//////@Composable
//////fun ImageS(){
//////    val imageUri = rememberSaveable { mutableStateOf("") }
//////    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty {R.drawable.flower1 })
//////    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//////        uri?.let { imageUri.value = it.toString() }
//////    }
//////    Box(contentAlignment = Alignment.Center) {
//////        Image(
//////            painter = painter,
//////            contentDescription = "Book Cover",
//////            contentScale = ContentScale.Crop,
//////            modifier = Modifier
//////                .size(100.dp)
//////                .border(1.dp, Color.White)
//////        )
//////        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
//////            Icon(
//////                imageVector = Icons.Filled.Add,
//////                contentDescription = "add Icon",
//////                tint = Color.Black,
//////                modifier = Modifier
//////                    .size(34.dp)
//////                    .clip(CircleShape)
//////                    .clickable { launcher.launch("image/*") }
//////            )
//////        }
//////    }
//////}