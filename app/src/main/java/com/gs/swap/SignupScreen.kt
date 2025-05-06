package com.gs.swap


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.gs.swap.R
import java.text.SimpleDateFormat
import java.util.*

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

        // Status Bar at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Home, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
            Text(
                text = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date()),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("100%", style = MaterialTheme.typography.bodySmall, color = Color.Black)
                Icon(Icons.Default.BatteryFull, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
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
                                imageVector = Icons.Default.Verified,
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

            // Bottom Section - signup button and login text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp), // Added bottom padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onSignupClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Sign Up", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onLoginClick) {
                    Text(
                        "Already have an account? Log in",
                        color = Color.Black
                    )
                }
            }
        }
    }
}