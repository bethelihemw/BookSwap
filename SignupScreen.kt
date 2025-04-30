package com.example.bookswap.screens

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

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import com.example.bookswap.R


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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                fontSize = 32.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Join our community of book lovers!",
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Full Name Field
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

            // Email Field
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

            // Password Field
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

            // Confirm Password Field
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
            Spacer(modifier = Modifier.height(54.dp))

            // Sign Up Button
            Button(
                onClick = onSignupClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Sign Up", fontSize = 16.sp, )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Login Link
            TextButton(onClick = onLoginClick) {
                Text(
                    "Already have an account? Log in",
                    color = Color.Black
                )
            }
        }
    }
}