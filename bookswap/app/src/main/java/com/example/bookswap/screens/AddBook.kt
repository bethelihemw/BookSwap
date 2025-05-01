package com.example.bookswap.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
//import com.example.bookswap.screens.color1
//import com.gs.swap.ui.theme.color2
import kotlin.text.ifEmpty

@Composable
fun AddBook() {
    var title by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    var language by rememberSaveable { mutableStateOf("") }
    var edition by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var selectedGenre by rememberSaveable { mutableStateOf("") }
    var expandedGenreDropdown by remember { mutableStateOf(false) }
    val genres = remember { listOf("Academic", "Fiction", "Mystery", "Educational", "Sci-Fi", "Fantasy", "Thriller") }
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty {R.drawable.novel1 })
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // Use fillMaxSize for the Column
            .background(Color(0xFFFCEEFF))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically) // Use spacedBy for consistent spacing
    ) {
        Text("Add Book", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)

        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painter,
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
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

        Text("tap to upload a photo", color = Color.Gray)

        CustomTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = "Title"
        )
        CustomTextField(
            value = author,
            onValueChange = { author = it },
            placeholder = "Author"
        )
        CustomTextField(
            value = language,
            onValueChange = { language = it },
            placeholder = "Language"
        )
        CustomTextField(
            value = edition,
            onValueChange = { edition = it },
            placeholder = "Edition"
        )
        CustomTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = "Description"
        )

        // Simplified Dropdown
        Box {
            OutlinedTextField(
                value = selectedGenre,
                onValueChange = { /* Do nothing as it's controlled by dropdown */ },
                label = { Text("Genre") },
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, "Dropdown", Modifier.clickable { expandedGenreDropdown = !expandedGenreDropdown })
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expandedGenreDropdown,
                onDismissRequest = { expandedGenreDropdown = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre) },
                        onClick = {
                            selectedGenre = genre
                            expandedGenreDropdown = false
                        }
                    )
                }
            }
        }

        GradientButton(
            text = "Submit",
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
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,

        modifier = Modifier.fillMaxWidth().height(50.dp)
    )
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .drawBehind {
                drawRect(gradient)
            },
        shape = RoundedCornerShape(4.dp) // Optional: Add rounded corners to the button
    ) {
        Text(text = text, color = textColor, fontWeight = FontWeight.Medium, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AddBookPreview() {
    AddBook()
}



















//package com.gs.swap
//
//
//import android.net.Uri
//import androidx.compose.ui.graphics.Color
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.sp
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.runtime.*
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.text.font.FontWeight
//import com.gs.swap.ui.theme.color1
//import com.gs.swap.ui.theme.color2
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import kotlin.text.ifEmpty
//import coil3.compose.rememberAsyncImagePainter
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.material.icons.filled.ArrowDropDown
//
//
//
//
//@Composable
//fun AddBook() {
//
//    var titleText by remember { mutableStateOf(TextFieldValue("")) }
//    var authorText by remember { mutableStateOf(TextFieldValue("")) }
//    var language by remember { mutableStateOf(TextFieldValue("")) }
//    var edition by remember { mutableStateOf(TextFieldValue("")) }
//    var description by remember { mutableStateOf(TextFieldValue("")) }
//    var genere by remember { mutableStateOf(TextFieldValue("")) }
//
//    Column(
//        modifier = Modifier.height(750.dp)
//            .background(Color(0xFFFCEEFF)) // light pink background
//            .padding(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Add Book" , fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
//
//
//
//
//        Box{
//            val imageUri = rememberSaveable { mutableStateOf("") }
//            val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.bases})
//            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
//                    uri:Uri? -> uri?.let{imageUri.value = it.toString()}
//            }
//            Column(
//
//                modifier = Modifier.size(100.dp)
//                    .padding(top = 25.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//
//                verticalArrangement = Arrangement.Center
//            ){
//                Image(painter = painter,
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .size(width = 100.dp,height=100.dp)
//                        .border(width =1.dp,color = Color.White)
//
//                )
//            }
//            Box(modifier = Modifier.padding(top = 100.dp, start = 260.dp)){
//                Icon(
//                    imageVector = Icons.Filled.Add,
//                    contentDescription = "add Icon", // For accessibility
//                    tint = Color.Black,
//                    modifier = Modifier.size(34.dp).clip(CircleShape).clickable{launcher.launch("image/*")} // Optional: Control the icon size
//                )
//            }
//        }
//
//
//
//        Text(text = "tap to upload a photo",color = Color.Gray)
//
//        CustomTextField(
//            value = titleText,
//            onValueChange = { titleText = it },
//            placeholder = "Title"
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomTextField(
//            value = authorText,
//            onValueChange = { authorText = it },
//            placeholder = "Author"
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomTextField(
//            value = language,
//            onValueChange = { language = it },
//            placeholder = "language"
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomTextField(
//            value = edition,
//            onValueChange = { edition = it },
//            placeholder = "edition"
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        CustomTextField(
//            value = description,
//            onValueChange = { description = it },
//            placeholder = "description"
//        )
//        Spacer(modifier = Modifier.height(3.dp))
//        YourScreen()
//        Spacer(modifier = Modifier.height(20.dp))
//        GradientButton(
//            text = "Submit",
//            textColor = Color.White,
//            gradient = Brush.horizontalGradient(
//                colors = listOf(
//                    color1,
//                    color2
//                )
//            )){}
//    }
//}
//@Composable
//fun SimpleDropdown(
//    options: List<String>,
//    onItemSelected: (String) -> Unit,
//    label: String = "Please select"
//) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedItem by remember { mutableStateOf("") }
//
//    Box {
//        Row(
//            modifier = Modifier
//                .clickable { expanded = !expanded }
//                .border(BorderStroke(1.dp, color = Color(0xFF9326D3)))
//                .height(46.dp)
//                .width(390.dp)
//                .padding(12.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = if (selectedItem.isNotEmpty()) selectedItem else label)
//            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
////            modifier = Modifier.fillMaxWidth()
//        ) {
//            options.forEach { option ->
//                DropdownMenuItem(
//                    text = { Text(option) },
//                    onClick = {
//                        selectedItem = option
//                        onItemSelected(option)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun YourScreen() {
//    val genres = listOf("Academic","Fiction", "Mystery", "Educational","Sci-Fi", "Fantasy", "Thriller")
//    var selectedGenre by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        SimpleDropdown(
//            options = genres,
//            onItemSelected = { genre -> selectedGenre = genre },
//            label = "Select Genre"
//        )
//    }
//}
//
//
//
//@Composable
//fun CustomTextField(
//    value: TextFieldValue,
//    onValueChange: (TextFieldValue) -> Unit,
//    placeholder: String
//) {
//    BasicTextField(
//        value = value,
//        onValueChange = onValueChange,
//        singleLine = true,
//        cursorBrush = SolidColor(Color(0xFF9326D3)), // purple cursor
//        textStyle = TextStyle(
//            color = Color.Black,
//            fontSize = 16.sp
//        ),
//        decorationBox = { innerTextField ->
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(46.dp)
//                    .drawBehind {
//                        val strokeWidth = 1f
//                        val y = size.height - strokeWidth / 2
//                        drawRect(
//                            color = Color(0xFF9326D3), // purple border
//                            size = size,
//                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
//                        )
//                    }
//                    .padding(horizontal = 10.dp, vertical = 8.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                if (value.text.isEmpty()) {
//                    Text(
//                        text = placeholder,
//                        color = Color.Gray,
//                        fontSize = 16.sp
//                    )
//                }
//                innerTextField()
//            }
//        },
//        modifier = Modifier.width(300.dp)
//    )
//}
//
//@Composable
//fun GradientButton(
//    text : String,
//    textColor: Color,
//    gradient: Brush,
//    onClick :()->Unit
//
//){
//    Column(
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) { Button(onClick={onClick()},
//        colors = ButtonDefaults.buttonColors(
//            containerColor = Color.Transparent)
//
//    )
//    {
//        Box(modifier = Modifier
//            .background(gradient)
//            .size(width = 300.dp,height = 50.dp)
//        ){
//            Text(text = text, color = textColor, fontWeight = FontWeight.Medium, fontSize = 20.sp,modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    } }
//
//}
//@Preview
//@Composable
//fun GradientButtonPreview() {
//    GradientButton(
//        text = "Submit",
//        textColor = Color.White,
//        gradient = Brush.horizontalGradient(
//            colors = listOf(
//                color1,
//                color2
//            )
//        )){}
//
//}
//
//
//
