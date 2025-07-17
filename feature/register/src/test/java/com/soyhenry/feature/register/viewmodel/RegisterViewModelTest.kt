package com.soyhenry.feature.register.viewmodel

import android.app.Application
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import com.soyhenry.core.domain.User
import com.soyhenry.feature.register.MainDispatcherRule
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.register.domain.usecase.RegisterUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@ExperimentalComposeRuntimeApi
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: RegisterViewModel
    private val FORM_ERROR_MESSAGE = "Se encontraron errores en el formulario, inténtelo de nuevo"

    @Before
    fun setup() {
        registerUseCase = mockk()
        userPreferences = mockk(relaxed = true)
        viewModel = RegisterViewModel(registerUseCase, userPreferences)
    }

    @Test
    fun `onRegisterClick with valid input triggers success`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        val fakeResponse = mockk<User>()
        coEvery { registerUseCase(any(), any(), any()) } returns fakeResponse

        viewModel.onRegisterClick()

        assertTrue(viewModel.registerSuccess.value)
        assertEquals("Registro exitoso", viewModel.toastMessage.value)
        coVerify { userPreferences.saveUser(fakeResponse) }
    }

    @Test
    fun `onRegisterClick with HTTP 409 shows email conflict error`() = runTest {
        viewModel.onEmailChange("conflict@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        val exception = mockk<HttpException> {
            coEvery { code() } returns 409
        }
        coEvery { registerUseCase(any(), any(), any()) } throws exception

        viewModel.onRegisterClick()

        assertFalse(viewModel.registerSuccess.value)
        assertTrue(viewModel.toastMessage.value!!.contains("El email ya está registrado"))
    }

    @Test
    fun `onRegisterClick with IOException shows network error`() = runTest {
        viewModel.onEmailChange("net@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        coEvery { registerUseCase(any(), any(), any()) } throws IOException("No connection")

        viewModel.onRegisterClick()

        assertTrue(viewModel.toastMessage.value!!.contains("Error de red. por favor, verifica tu conexión"))
    }

    @Test
    fun `clearToastMessage sets toastMessage to null`() {
        viewModel.onEmailChange("test@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        viewModel.clearToastMessage()

        assertNull(viewModel.toastMessage.value)
    }

    @Test
    fun `onRegisterClick with invalid email sets email error`() = runTest {
        viewModel.onEmailChange("invalid-email")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        viewModel.onRegisterClick()

        assertEquals("Formato de email inválido", viewModel.emailError.value)
        assertEquals(FORM_ERROR_MESSAGE, viewModel.toastMessage.value)
    }

    @Test
    fun `onRegisterClick with empty name sets name error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onNameChange("")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("password123")

        viewModel.onRegisterClick()

        assertEquals("El nombre es obligatorio", viewModel.nameError.value)
        assertEquals(FORM_ERROR_MESSAGE, viewModel.toastMessage.value)
    }

    @Test
    fun `onRegisterClick with short password sets password error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("short")
        viewModel.onConfirmPasswordChange("short")

        viewModel.onRegisterClick()

        assertEquals("Debe tener al menos 8 caracteres", viewModel.passwordError.value)
        assertEquals(FORM_ERROR_MESSAGE, viewModel.toastMessage.value)
    }

    @Test
    fun `onRegisterClick with mismatched confirm password sets confirm password error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onNameChange("Henry")
        viewModel.onPasswordChange("password123")
        viewModel.onConfirmPasswordChange("differentPassword")

        viewModel.onRegisterClick()

        assertEquals("Las contraseñas no coinciden", viewModel.confirmPasswordError.value)
        assertEquals("Se encontraron errores en el formulario, inténtelo de nuevo", viewModel.toastMessage.value)
    }
}
