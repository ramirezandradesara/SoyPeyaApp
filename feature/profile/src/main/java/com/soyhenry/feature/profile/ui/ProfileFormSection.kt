package com.soyhenry.feature.profile.ui

import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import androidx.compose.ui.layout.ContentScale
import com.soyhenry.library.ui.components.textField.PasswordTextField
import androidx.compose.ui.res.stringResource
import com.soyhenry.core.model.domain.User
import com.soyhenry.feature.profile.R
import com.soyhenry.library.ui.components.LoadImage
import com.soyhenry.library.ui.components.textField.EmailTextField

@Composable
fun ProfileFormSection(
    profile: User,
    imageUri: Uri?,
    isImageUploading: Boolean,
    onImageSelected: (Uri?) -> Unit,
    onSave: (updatedUser: User, imageUri: Uri?) -> Unit
) {
    var name by remember(profile.fullName) { mutableStateOf(profile.fullName) }
    var email by remember(profile.email) { mutableStateOf(profile.email) }
    var password by remember(profile.encryptedPassword) { mutableStateOf(profile.encryptedPassword) }

    val context = LocalContext.current

    val sdkInt = Build.VERSION.SDK_INT
    val permissionToRequest = if (sdkInt >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageSelected(uri)
        }
    )

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                imagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "Permiso denegado para acceder a la galería", Toast.LENGTH_SHORT).show()
            }
        }
    )

    fun selectImageWithPermission() {
        storagePermissionLauncher.launch(permissionToRequest)
    }

    if (profile.imageUrl.isEmpty().not()) {
        LoadImage(
            url = profile.imageUrl,
            contentDescription = profile.fullName,
            modifier = Modifier.size(120.dp)
        )
    } else {
        Card(
            modifier = Modifier
                .size(120.dp)
                .clickable { selectImageWithPermission() }
        ) {
            if (imageUri != null) {
                val bitmap = remember(imageUri) {
                    try {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(
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
        modifier = Modifier.fillMaxWidth()
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
                encryptedPassword = password
            )
            onSave(updatedUser, imageUri)
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(stringResource(id = R.string.save_changes_button))
    }
}
