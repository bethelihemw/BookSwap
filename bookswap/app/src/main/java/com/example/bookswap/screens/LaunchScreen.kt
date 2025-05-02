package com.example.bookswap.screens

import com.example.bookswap.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// A simple data class representing the UI state
data class LaunchUiState(
    val isLoading: Boolean = false,
    val isReady: Boolean = false,
    val errorMessage: String? = null
)


@Composable
fun LaunchScreen(
    uiState: LaunchUiState,
    onStartClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()

    ) {
        //background Image
        Image(
            painter = painterResource(id=R.drawable.launchbackground),
            contentDescription="Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Main Content Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                uiState.errorMessage != null -> {
                    ErrorIcon()
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp
                    )
                }
                else -> {
                    AppIcon()
                    Spacer(modifier = Modifier.height(32.dp))
                    AppTitle()
                    Spacer(modifier = Modifier.height(16.dp))
                    AppSlogan()
                }
            }
        }

// Start Button (only shown when ready)
        if (uiState.isReady && !uiState.isLoading) {
            Button(
                onClick = onStartClicked,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFAA1FB7),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Start Swapping",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

