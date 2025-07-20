package com.soyhenry.feature.profile.ui

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.soyhenry.feature.profile.viewmodel.ProfileViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.soyhenry.library.ui.components.container.ViewContainer
import androidx.compose.ui.res.stringResource
import com.soyhenry.core.approutes.AppRoutes
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.profile.R
import com.soyhenry.library.ui.components.LoadingScreen
import com.soyhenry.library.ui.components.state.ErrorState

@Composable
fun ProfileView(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by profileViewModel.uiState.collectAsState()
    val isImageUploading by profileViewModel.isImageUploading.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val toastMessage by profileViewModel.toastMessage.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    toastMessage?.let { message ->
        LaunchedEffect(toastMessage) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            profileViewModel.clearToastMessage()
        }
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
                LoadingScreen()
            }

            is UiState.Success -> {
                val profile = state.data

                ProfileFormSection(
                    profile = profile,
                    imageUri = imageUri,
                    isImageUploading = isImageUploading,
                    onImageSelected = { uri -> imageUri = uri },
                    onSave = { updatedUser, uri -> profileViewModel.updateProfile(updatedUser, uri) }
                )
            }

            is UiState.Error -> {
                ErrorState(message = state.message)
            }
        }
    }
}
