import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.gs.myapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gs.myapp.ui.theme.color1
import com.gs.myapp.ui.theme.color2
import androidx.compose.ui.graphics.takeOrElse

@Composable
fun Description() {
    // Sample book details (replace with actual data)
    val bookTitle = "The Lord Of The Rings"
    val ownerName = "J.R.R Tolkien"
    val ownerEmail = "jrr.tolkien@example.com"
    val bookDescription = "A hobbit named Frodo inherits the One Ring, which can destroy the " +
            "entire world. With the recently reawakened evil, being Sauron, going" +
            " after the Ring to cement his reign," +
            " Frodo joins with eight others to destroy the Ring and defeat Sauron."

    var isSwapInfoVisible by remember { mutableStateOf(false) }
    val gradient = Brush.horizontalGradient(colors = listOf(color1, color2))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.width(100.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow",
                tint = Color.Blue,
                modifier = Modifier
                    .size(24.dp)
//                    .clickable {
//                        navController.popBackStack() // Go back to the previous screen
//                    }
            )
            Spacer(modifier = Modifier.width(140.dp))
            Text(text = "Book Detail", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        }

        Image(
            painter = painterResource(id = R.drawable.novel1),
            contentDescription = "Book Cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(190.dp)
                .height(280.dp)
        )
        Text(text = bookTitle, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "Owner: $ownerName", color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = bookDescription)
        Spacer(modifier = Modifier.width(28.dp))

        // Request Swap Button with Gradient Background
        Button(
            onClick = { isSwapInfoVisible = !isSwapInfoVisible },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make the default container transparent
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth().background(gradient, shape = ButtonDefaults.shape).size(width = 150.dp, height = 40.dp) // Apply gradient to the background
        ) {
            Text(text = "Request Swap")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = if (isSwapInfoVisible) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (isSwapInfoVisible) "Hide Info" else "Show Info",
                tint = Color.White
            )
        }

        // Dropdown/Revealed Message
        AnimatedVisibility(
            visible = isSwapInfoVisible,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Owner Name: $ownerName", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Owner Email: $ownerEmail", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "You can connect with the owner via the provided email to request a swap.", fontSize = 14.sp)
            }
        }
    }
}
