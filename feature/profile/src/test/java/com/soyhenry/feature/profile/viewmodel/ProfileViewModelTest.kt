package com.soyhenry.feature.profile.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import com.cloudinary.Cloudinary
import com.soyhenry.core.model.domain.User
import com.soyhenry.core.model.state.UiState
import com.soyhenry.feature.profile.MainDispatcherRule
import com.soyhenry.feature.profile.domain.usecase.GetUserUseCase
import com.soyhenry.feature.profile.domain.usecase.SaveUserUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStream

@ExperimentalComposeRuntimeApi
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var saveUserUseCase: SaveUserUseCase
    private lateinit var cloudinary: Cloudinary
    private lateinit var application: Application
    private lateinit var viewModel: ProfileViewModel

    private val fakeUser = User(
        id = "1",
        fullName = "Test",
        email = "test@example.com",
        encryptedPassword = "me0nN9dVNYtLBu",
        imageUrl = ""
    )

    @Before
    fun setup() {
        getUserUseCase = mockk()
        saveUserUseCase = mockk(relaxed = true)
        cloudinary = mockk()
        application = mockk()

        viewModel = ProfileViewModel(
            application,
            saveUserUseCase,
            getUserUseCase,
            cloudinary
        )
    }

    @Test
    fun `loadProfile returns user and updates state to success`() = runTest {
        coEvery { getUserUseCase() } returns fakeUser

        viewModel.loadProfile()

        assertTrue(viewModel.uiState.value is UiState.Success)
        assertEquals(fakeUser, (viewModel.uiState.value as UiState.Success).data)
    }

    @Test
    fun `loadProfile returns null and updates state to error`() = runTest {
        coEvery { getUserUseCase() } returns null

        viewModel.loadProfile()

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertEquals("No se encontró un usuario guardado", (viewModel.uiState.value as UiState.Error).message)
    }

    @Test
    fun `updateProfile without image updates user and shows toast`() = runTest {
        viewModel.updateProfile(fakeUser, null)

        coVerify { saveUserUseCase(fakeUser) }
        assertEquals("Perfil actualizado", viewModel.toastMessage.value)
        assertTrue(viewModel.uiState.value is UiState.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `updateProfile with image uploads image and updates user`() = runTest {
        val imageUri = mockk<Uri>()
        val contentResolver = mockk<ContentResolver>()
        val inputStream = mockk<InputStream>()
        val uploadResult = mapOf("secure_url" to "https://image.url/test.png")

        every { application.contentResolver } returns contentResolver
        every { contentResolver.openInputStream(imageUri) } returns inputStream
        coEvery { cloudinary.uploader().upload(inputStream, any()) } returns uploadResult

        viewModel.updateProfile(fakeUser, imageUri)

        advanceUntilIdle()

        coVerify { saveUserUseCase(match { it.imageUrl == "https://image.url/test.png" }) }
        assertEquals("Perfil e imagen actualizados", viewModel.toastMessage.value)
        assertTrue(viewModel.uiState.value is UiState.Success)
    }

    @Test
    fun `updateProfile throws exception and sets error`() = runTest {
        coEvery { saveUserUseCase(any()) } throws RuntimeException("Failed")

        viewModel.updateProfile(fakeUser, null)

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertTrue((viewModel.uiState.value as UiState.Error).message.contains("Error al guardar el perfil"))
    }

    @Test
    fun `clearToastMessage sets toastMessage to null`() {
        viewModel.clearToastMessage()
        assertNull(viewModel.toastMessage.value)
    }
}
