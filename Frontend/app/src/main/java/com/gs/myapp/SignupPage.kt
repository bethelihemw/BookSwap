package com.gs.myapp


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.gs.myapp.ui.theme.color1
import com.gs.myapp.ui.theme.color2
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.ifEmpty

@Composable
fun SignupScreen(
    onSignupClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_signup),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        val imageUri = rememberSaveable { mutableStateOf("") }
        val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.flower1 })
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri.value = it.toString() }
        }

        Box(
            modifier = Modifier

                .padding(vertical = 120.dp, horizontal = 120.dp
                ), 
            contentAlignment = Alignment.TopCenter // Align content to the top center
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painter,
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White)
                )
                Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add Icon",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .clickable { launcher.launch("image/*") }
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title Section - moved up with less top padding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp), // Reduced from 80dp
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Join our community of book lovers!",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }

            // Form Fields - centered vertically using weight
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Enter your full name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter your Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Create a password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password icon"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password"
                                    else "Show password"
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (confirmPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Confirm password icon"
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible) "Hide password"
                                    else "Show password"
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }
            GradientButton(
                text = "Sign Up",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        color1,
                        color2
                    )
                )
            ) {
                // TODO: Handle submit logic
            }
            Spacer(modifier = Modifier.height(1.dp))

                TextButton(onClick = onLoginClick) {
                    Text(
                        "Already have an account? Log in",
                        color = Color.Black
                    )
                }
        }
    }
