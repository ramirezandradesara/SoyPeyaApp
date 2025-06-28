package com.soyhenry.feature.profile.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.feature.profile.data.model.Profile
import com.soyhenry.feature.profile.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): ViewModel()  {

    private val _isImageUploading =  MutableStateFlow(true)
    val isImageUploading: StateFlow<Boolean> = _isImageUploading.asStateFlow()

    private val _profile = MutableStateFlow(Profile())
    val profile = _profile.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile(){
        viewModelScope.launch {
            _profile.value = repository.getProfile()
        }
    }
}
