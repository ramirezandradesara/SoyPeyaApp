package com.soyhenry.feature.profile.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.soyhenry.feature.profile.R
import com.soyhenry.library.ui.components.LoadImage

@Composable
fun ProfileImageSelector(
    profileImageUrl: String,
    imageUri: Uri?,
    onSelectImage: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (profileImageUrl.isNotEmpty()) {
            LoadImage(
                url = profileImageUrl,
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { onSelectImage() },
                onClick = onSelectImage
            )
        } else {
            Card(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { onSelectImage() },
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                if (imageUri != null) {
                    val bitmap = remember(imageUri) {
                        getBitmapFromUri(context, imageUri)
                    }

                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = stringResource(id = R.string.profile_title),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.error_uploading_image),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.select_image),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        @Suppress("DEPRECATION")
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    } catch (e: Exception) {
        null
    }
}
