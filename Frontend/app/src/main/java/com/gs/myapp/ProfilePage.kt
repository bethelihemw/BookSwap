package com.gs.myapp//package com.gs.myapp
//
//
//
//import android.os.Bundle
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
////import androidx.compose.material.icons.filled.Visibility
////import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//
//
//@Composable fun ProfileScreen() {
//    var name by remember { mutableStateOf("John Doe") }
//    val email = "john.doe@example.com"
//    var newPassword by remember { mutableStateOf("12345678") }
//    var showPassword by remember { mutableStateOf(false) }
//    var isEditing by remember { mutableStateOf(false) }
//    var isLoading by remember { mutableStateOf(false) }
//    var showLogoutDialog by remember { mutableStateOf(false) }
//    var showDeleteDialog by remember { mutableStateOf(false) }
//
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        //background Image
//        Image(
//            painter = painterResource(id = R.drawable.flower1),
//            contentDescription = "Background Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(Modifier.height(40.dp))
//            Text(
//                text = "Profile",
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//            Spacer(Modifier.height(40.dp))
//
//            // Profile Icon
//            Icon(
//                imageVector = Icons.Filled.AccountCircle,
//                contentDescription = "Profile",
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(CircleShape),
//                tint = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(Modifier.height(40.dp))
//
//            // Username Field
//            TextField(
//                value = name,
//                onValueChange = { name = it },
//                label = { Text("Username") },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = isEditing,
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Black,
//                    unfocusedIndicatorColor = Color.Gray,
//                    disabledIndicatorColor = Color.Transparent,
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    cursorColor = Color.Black
//                ),
//                singleLine = true,
//                leadingIcon = {
//                    IconButton(onClick = { showPassword = !showPassword }) {
//                        Icon(
//                            imageVector = Icons.Filled.Person,
//                            contentDescription = "Profile"
//                        )
//                    }
//                }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Email Field
//            TextField(
//                value = email,
//                onValueChange = {},
//                label = { Text("Email") },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = isEditing,
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Black,
//                    unfocusedIndicatorColor = Color.Gray,
//                    disabledIndicatorColor = Color.Transparent,
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    cursorColor = Color.Black
//                ),
//                singleLine = true,
//                leadingIcon = {
//                    IconButton(onClick = { showPassword = !showPassword }) {
//                        Icon(
//                            imageVector = Icons.Default.Email,
//                            contentDescription = "Email"
//                        )
//                    }
//                }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            //Password
//            TextField(
//                value = newPassword,
//                onValueChange = { newPassword = it },
//                label = { Text("New Password") },
//                enabled = isEditing,
//                colors = TextFieldDefaults.colors(
//                    focusedIndicatorColor = Color.Black,
//                    unfocusedIndicatorColor = Color.Gray,
//                    disabledIndicatorColor = Color.Transparent,
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    cursorColor = Color.Black
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent),
//                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Lock,
//                        contentDescription = "Password"
//                    )
//                },
//                trailingIcon = {
//                    IconButton(onClick = { showPassword = !showPassword }) {
//                        Icon(
//                            imageVector = if (showPassword) Icons.Filled.Lock else Icons.Filled.Visibility,
//                            contentDescription = if (showPassword) "Hide Password" else "Show Password"
//                        )
//                    }
//                }
//            )
//
//            Spacer(Modifier.height(16.dp))
//
//            // Logout Button
//            TextButton(
//                onClick = { showLogoutDialog = true },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 4.dp),
//                colors = ButtonDefaults.outlinedButtonColors(
//                    contentColor = Color.Red,
//                    containerColor = Color.Transparent
//                )
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentWidth(Alignment.Start),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ExitToApp,
//                        contentDescription = "Log Out",
//                        tint = Color.Red,
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "Logging Out",
//                        color = Color.Red,
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                }
//            }
//            Divider(modifier = Modifier.width(500.dp), thickness = 2.dp, color = Color.Black)
//
//            Spacer(Modifier.height(16.dp))
//
//            //Delete Button
//            TextButton(
//                onClick = { showDeleteDialog = true },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 4.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentWidth(Alignment.Start), // Align content to the start (left)
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete",
//                        tint = Color.Red,
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = "Delete Account",
//                        color = Color.Red,
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                }
//            }
//
//            Divider(modifier = Modifier.width(500.dp), thickness = 2.dp, color = Color.Black)
//
//            // Edit / Save Button
//            Spacer(modifier = Modifier.height(64.dp))
//            Button(
//                onClick = {
//                    if (isEditing) {
//                        // Save logic here (if needed)
//                    }
//                    isEditing = !isEditing
//                },
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)
//            ) {
//                Text(if (isEditing) "Save Profile" else "Edit Profile")
//            }
//
//            Spacer(Modifier.height(32.dp))
//
//
//        }
//
//        // Loading Indicator
//        if (isLoading) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black.copy(alpha = 0.5f)),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//        // Logout Dialog
//        if (showLogoutDialog) {
//            AlertDialog(
//                onDismissRequest = { showLogoutDialog = false },
//                title = { Text("Log Out") },
//                text = { Text("Are you sure you want to log out?") },
//                confirmButton = {
//                    TextButton(onClick = {
//                        showLogoutDialog = false
//                        // Handle logout logic here
//                    }) {
//                        Text("Confirm")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { showLogoutDialog = false }) {
//                        Text("Cancel")
//                    }
//                }
//            )
//        }
//
//        // Delete Account Dialog
//        if (showDeleteDialog) {
//            AlertDialog(
//                onDismissRequest = { showDeleteDialog = false },
//                title = { Text("Delete Account") },
//                text = { Text("This will permanently delete your account and all data.") },
//                confirmButton = {
//                    TextButton(onClick = {
//                        showDeleteDialog = false
//                        // Handle deletion logic here
//                    }) {
//                        Text("Delete", color = Color.Red)
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { showDeleteDialog = false }) {
//                        Text("Cancel")
//                    }
//                }
//            )
//        }
//    }
//}