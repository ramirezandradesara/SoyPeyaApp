package com.soyhenry.feature.profile.viewmodel


import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.Cloudinary
import com.soyhenry.feature.profile.data.model.ProfileModel
import com.soyhenry.feature.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val myApplication: Application,
    private val repository: ProfileRepository,
    val cloudinary: Cloudinary
): AndroidViewModel(myApplication)  {

    private val _profile = MutableStateFlow(ProfileModel())
    val profile: StateFlow<ProfileModel> = _profile.asStateFlow()

    private val _isImageUploading =  MutableStateFlow(false)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }

    fun updateProfile(newProfile: ProfileModel, imageUri: Uri?) {
        _profile.value = newProfile
        if (imageUri != null) {
            uploadImage(imageUri)
        }
        // profile.dataSource.updateProfileInfo()
    }

    private fun uploadImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isImageUploading.value = true
            try {
                val inputStream = getApplication<Application>().contentResolver.openInputStream(uri)
                val uploadResult = cloudinary.uploader().upload(inputStream, mapOf("upload_preset" to "soypeya-example"))
                val imageUrl = uploadResult["secure_url"] as String
                val updatedProfile = _profile.value.copy(image = imageUrl)
                _profile.value = updatedProfile
            } catch (e: Exception) {
                Log.e("Cloudinary", "Error uploading image", e)
            } finally {
                _isImageUploading.value = false
            }
        }
    }
}
