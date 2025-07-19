package com.soyhenry.feature.login.viewmodel

import com.soyhenry.core.domain.User
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.feature.login.MainDispatcherRule
import com.soyhenry.feature.login.domain.usecase.LoginUseCase
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
import com.soyhenry.data.remote.model.LoginResult

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: LoginViewModel
    private val FORM_ERROR_MESSAGE = "Se encontraron errores en el formulario"

    @Before
    fun setup() {
        loginUseCase = mockk()
        userPreferences = mockk(relaxed = true)
        viewModel = LoginViewModel(loginUseCase, userPreferences)
    }

    @Test
    fun `onLoginClick with valid input triggers success`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        val expectedUser = mockk<User>()
        val fakeResponse = mockk<LoginResult> {
            coEvery { user } returns expectedUser
            coEvery { message } returns "Login exitoso"
        }
        coEvery { loginUseCase(any(), any()) } returns fakeResponse

        viewModel.onLoginClick()

        assertEquals(true, viewModel.loginSuccess.value)
        assertEquals("Login exitoso", viewModel.toastMessage.value)
        coVerify { userPreferences.saveUser(expectedUser) }
    }

    @Test
    fun `onLoginClick with invalid email sets error`() = runTest {
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")

        viewModel.onLoginClick()

        assertEquals("Formato de email inválido", viewModel.emailError.value)
        assertEquals(FORM_ERROR_MESSAGE, viewModel.toastMessage.value)
    }

    @Test
    fun `onLoginClick with short password sets error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("short")

        viewModel.onLoginClick()

        assertEquals("Debe tener al menos 8 caracteres", viewModel.passwordError.value)
        assertEquals(FORM_ERROR_MESSAGE, viewModel.toastMessage.value)
    }

    @Test
    fun `onLoginClick with HTTP 401 returns invalid credentials`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        val exception = mockk<HttpException> {
            coEvery { code() } returns 401
        }
        coEvery { loginUseCase(any(), any()) } throws exception

        viewModel.onLoginClick()

        assertEquals("Credenciales inválidas", viewModel.toastMessage.value)
        assertEquals(false, viewModel.loginSuccess.value)
    }

    @Test
    fun `onLoginClick with HTTP 404 returns user not found`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        val exception = mockk<HttpException> {
            coEvery { code() } returns 404
        }
        coEvery { loginUseCase(any(), any()) } throws exception

        viewModel.onLoginClick()

        assertEquals("Usuario no encontrado", viewModel.toastMessage.value)
    }

    @Test
    fun `onLoginClick with HTTP 500 returns server error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        val exception = mockk<HttpException> {
            coEvery { code() } returns 500
        }
        coEvery { loginUseCase(any(), any()) } throws exception

        viewModel.onLoginClick()

        assertEquals("Error del servidor. Por favor, inténtalo de nuevo más tarde", viewModel.toastMessage.value)
    }

    @Test
    fun `onLoginClick with IOException returns network error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        coEvery { loginUseCase(any(), any()) } throws IOException()

        viewModel.onLoginClick()

        assertEquals("Error de red. Verifica tu conexión", viewModel.toastMessage.value)
    }

    @Test
    fun `onLoginClick with unexpected exception returns generic error`() = runTest {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("password123")

        coEvery { loginUseCase(any(), any()) } throws RuntimeException("Crash")

        viewModel.onLoginClick()

        assertTrue(viewModel.toastMessage.value!!.contains("Error inesperado"))
    }

    @Test
    fun `clearToastMessage sets toastMessage to null`() {
        viewModel.clearToastMessage()
        assertNull(viewModel.toastMessage.value)
    }
}
