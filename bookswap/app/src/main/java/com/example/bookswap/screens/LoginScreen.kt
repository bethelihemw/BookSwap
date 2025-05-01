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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookswap.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = null,
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
                .padding(horizontal = 16.dp)
                .padding(top = 80.dp), // Added top padding for status bar
            verticalArrangement = Arrangement.Top, // Changed from Center to Top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp)) // Reduced initial spacing

            Text(
                text = "BookSwap!",
                fontSize = 48.sp, // Reduced font size
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Welcome Back! Please Log in to continue",
                fontSize = 16.sp, // Reduced font size
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(48.dp)) // Adjusted spacing

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Enter your email or username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email icon"
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter your password") },
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
                            imageVector = if (passwordVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp), // Added fixed height
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Log in", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onSignupClick) {
                Text("Need to create an account? Sign Up")
            }

            // Add flexible spacer at bottom to push content up
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}