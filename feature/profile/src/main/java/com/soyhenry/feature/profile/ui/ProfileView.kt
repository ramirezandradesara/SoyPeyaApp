package com.soyhenry.feature.profile.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.feature.profile.viewmodel.ProfileViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Card
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.soyhenry.feature.profile.data.model.Profile
import com.soyhenry.library.ui.components.PasswordTextField

@Composable
fun ProfileView(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val profile by profileViewModel.profile.collectAsState()
    var name by remember(profile.name) { mutableStateOf(profile.name) }
    var lastName by remember(profile.lastName) { mutableStateOf(profile.lastName) }
    var email by remember(profile.email) { mutableStateOf(profile.email) }
    var password by remember(profile.password) { mutableStateOf(profile.password) }
    var nationality by remember(profile.nationality) { mutableStateOf(profile.nationality) }

    val isImageUploading by profileViewModel.isImageUploading.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Profile 👩🏼",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Alert Dialog for image uploading
        if (isImageUploading) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {},
                title = { Text(text = "Subiendo imagen") },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        }

        if (profile.image.isNotEmpty()) {
            imageUri = null
            LoadImage(
                url = profile.image, contentDescription = profile.name,
                modifier = Modifier.size(50.dp)
            )
        } else {
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { launcher.launch("image/*") }
            ) {
                if (imageUri != null) {
                    val bitmap = remember(imageUri) {
                        try {
                            @Suppress("DEPRECATION")
                            android.provider.MediaStore.Images.Media.getBitmap(
                                context.contentResolver,
                                imageUri
                            ).asImageBitmap()
                        } catch (e: Exception) {
                            null
                        }
                    }

                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Error al cargar")
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Seleccionar Imagen")
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = nationality,
            onValueChange = { nationality = it },
            label = { Text("Nationality") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val updateProfile = Profile(
                    name = name,
                    lastName = lastName,
                    email = email,
                    password = password,
                    nationality = nationality,
                    image = profile.image
                )
                profileViewModel.updateProfile(updateProfile, imageUri)
            },
            enabled = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Save changes")
        }
    }
}

@Composable
fun LoadImage(url: String, contentDescription: String, modifier: Modifier) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
