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
import kotlinx.coroutines.flow.first

@HiltViewModel
class ProfileViewModel @Inject constructor(
    myApplication: Application,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val cloudinary: Cloudinary,
    private val userPreferences: UserPreferences
): AndroidViewModel(myApplication)  {

    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _isImageUploading =  MutableStateFlow(false)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val user = userPreferences.user.first()
                if (user != null) {
                    _uiState.value = UiState.Success(user)
                } else {
                    _uiState.value = UiState.Error("No se encontró un usuario guardado")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Ocurrió un error al cargar el perfil")
            }
        }
    }

    fun updateProfile(newProfile: User, imageUri: Uri?) {
        viewModelScope.launch {
            try {
                if (imageUri == null) {
                    userPreferences.saveUser(newProfile)
                    _uiState.value = UiState.Success(newProfile)
                    _toastMessage.value = "Perfil actualizado"
                } else {
                    uploadImage(imageUri, newProfile)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al guardar el perfil: ${e.message}")
            }
        }
    }

    private fun uploadImage(uri: Uri, baseUser: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _isImageUploading.value = true
            try {
                val inputStream = getApplication<Application>().contentResolver.openInputStream(uri)
                val uploadResult = cloudinary.uploader().upload(
                    inputStream, mapOf("upload_preset" to "soypeya-example")
                )
                val imageUrl = uploadResult["secure_url"] as String

                val updatedUser = baseUser.copy(imageUrl = imageUrl)
                userPreferences.saveUser(updatedUser)

                _uiState.value = UiState.Success(updatedUser)
                _toastMessage.value = "Perfil e imagen actualizados 🎉"
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error al subir imagen: ${e.message}")
            } finally {
                _isImageUploading.value = false
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
