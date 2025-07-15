package com.soyhenry.feature.profile.ui

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.soyhenry.library.ui.components.container.ViewContainer
import com.soyhenry.library.ui.components.textField.PasswordTextField
import androidx.compose.ui.res.stringResource
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.profile.R
import com.soyhenry.library.ui.components.textField.EmailTextField

@Composable
fun ProfileView(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isImageUploading by profileViewModel.isImageUploading.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    ViewContainer(
        title = stringResource(id = R.string.profile_title),
        icon = {
            IconButton(onClick = { navController.navigate(AppRoutes.Orders.route) }) {
                Icon(
                    imageVector = Icons.Default.Receipt,
                    contentDescription = stringResource(id = R.string.view_orders)
                )
            }
        }
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${state.message}")
                }
            }

            is UiState.Success -> {
                val profile = state.data

                var name by remember(profile.fullName) { mutableStateOf(profile.fullName) }
                var email by remember(profile.email) { mutableStateOf(profile.email) }
                var password by remember(profile.password) { mutableStateOf(profile.password) }

                if (isImageUploading) {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {},
                        title = { Text(text = stringResource(id = R.string.uploading_image)) },
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

                if (profile.image.isNullOrEmpty().not()) {
                    imageUri = null
                    LoadImage(
                        url = profile.image!!,
                        contentDescription = profile.fullName,
                        modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally)
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
                                    MediaStore.Images.Media.getBitmap(
                                        context.contentResolver,
                                        imageUri
                                    ).asImageBitmap()
                                } catch (e:  Exception) {
                                    null
                                }
                            }

                            if (bitmap != null) {
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = stringResource(id = R.string.profile_title),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(stringResource(id = R.string.error_uploading_image))
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(stringResource(id = R.string.select_image))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(id = R.string.name_label)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                EmailTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(8.dp))

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(id = R.string.password_label),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        val updatedUser = profile.copy(
                            fullName = name,
                            email = email,
                            password = password
                        )
                        profileViewModel.updateProfile(updatedUser, imageUri)
                    },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(stringResource(id = R.string.save_changes_button))
                }
            }
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
