package com.soyhenry.feature.profile.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.soyhenry.core.domain.User
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    myApplication: Application,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val cloudinary: Cloudinary,
    private val userPreferences: UserPreferences
): AndroidViewModel(myApplication)  {

    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState

    private val _isImageUploading =  MutableStateFlow(false)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            userPreferences.userEmail.collect { email ->
                if (!email.isNullOrEmpty()) {
                    _uiState.value = UiState.Success(getUserProfileUseCase(email))
                }
            }
        }
    }

    fun updateProfile(newProfile: User, imageUri: Uri?) {
        _uiState.value = UiState.Success(newProfile)

        if (imageUri != null) {
            uploadImage(imageUri)
        }
        // TODO() actualizar
    }

    private fun uploadImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isImageUploading.value = true
            try {
                val inputStream = getApplication<Application>().contentResolver.openInputStream(uri)
                val uploadResult = cloudinary.uploader().upload(inputStream, mapOf("upload_preset" to "soypeya-example"))
                val imageUrl = uploadResult["secure_url"] as String

                val updatedProfile = (_uiState.value as? UiState.Success)?.data?.copy(image = imageUrl)
                if (updatedProfile != null) {
                    _uiState.value = UiState.Success(updatedProfile)
                }
            } catch (e: Exception) {
                Log.e("Cloudinary", "Error uploading image", e)
            } finally {
                _isImageUploading.value = false
            }
        }
    }
}
