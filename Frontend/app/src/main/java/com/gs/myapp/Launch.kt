package com.gs.myapp


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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gs.myapp.GradientButton
import com.gs.myapp.ui.theme.color1
import com.gs.myapp.ui.theme.color2


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
            painter = painterResource(id=R.drawable.launcher_background),
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
            Spacer(modifier = Modifier.height(100.dp))
            GradientButton(
                text = "Start Swapping",
                textColor = Color.White,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        color1,
                        color2
                    )
                )
            ) {
//                navController.navigate("screenB")
                // TODO: Handle submit logic
                onStartClicked()
            }



        }

    }
}

@Composable
private fun AppIcon() {
    Image(
        painter = painterResource(id=R.drawable.launch),
        contentDescription="Logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(140.dp)
    )
}

@Composable
private fun ErrorIcon() {
    Icon(
        imageVector = Icons.Default.Build,
        contentDescription = "Error Icon",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(120.dp)
    )
}

@Composable
private fun AppTitle() {
    Text(
        text = "BookSwap",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun AppSlogan() {
    Text(
        text = "Welcome to BookSwap : Exchange your favourite books easily ! ",
        fontSize = 20.sp,
        fontWeight = FontWeight.Light,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}

