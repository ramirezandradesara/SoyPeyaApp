package com.soyhenry.data.repository

import com.soyhenry.core.domain.User
import com.soyhenry.core.session.UserPreferences
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.remote.datasource.UserRemoteDataSource
import com.soyhenry.data.remote.dto.LoginResponseDto
import com.soyhenry.data.remote.dto.UserDto
import com.soyhenry.data.remote.model.LoginRequest
import com.soyhenry.data.remote.model.RegisterRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private val remote: UserRemoteDataSource = mockk()
    private val userPreferences: UserPreferences = mockk()
    private lateinit var repository: UserRepositoryImpl

    private val user = User(
        id = "1",
        fullName = "Test User",
        email = "test@example.com",
        encryptedPassword = "me0nN9dVNYtLBu",
        imageUrl = ""
    )
    private val userDto = UserDto(
        id = "1",
        fullName = "Test User",
        email = "test@example.com",
        encryptedPassword = "me0nN9dVNYtLBu",
        createdAt = "2025-07-18T00:00:00Z",
        updatedAt = "2025-07-18T01:00:00Z"
    )
    private val registerRequest = RegisterRequest("test", "test@example.com", "password")
    private val loginRequest = LoginRequest("test@example.com", "password")
    private val remoteLoginResponse = LoginResponseDto(message = "Success", user = userDto)

    @Before
    fun setup() {
        repository = UserRepositoryImpl(remote, userPreferences)
    }

    @Test
    fun `getProfileByEmail returns mapped user`() = runTest {
        coEvery { remote.getProfileByEmail("test@example.com") } returns userDto

        val result = repository.getProfileByEmail("test@example.com")

        assertEquals(user, result)
        coVerify(exactly = 1) { remote.getProfileByEmail("test@example.com") }
    }

    @Test
    fun `registerUser returns mapped user`() = runTest {
        coEvery { remote.registerUser(registerRequest) } returns userDto

        val result = repository.registerUser(registerRequest)

        assertEquals(user, result)
        coVerify(exactly = 1) { remote.registerUser(registerRequest) }
    }

    @Test
    fun `loginUser returns mapped login result`() = runTest {
        coEvery { remote.loginUser(loginRequest) } returns remoteLoginResponse

        val result = repository.loginUser(loginRequest)

        assertEquals(remoteLoginResponse.message, result.message)
        assertEquals(remoteLoginResponse.user.toDomain(), result.user)
        coVerify(exactly = 1) { remote.loginUser(loginRequest) }
    }

    @Test
    fun `getUser returns user from preferences`() = runTest {
        coEvery { userPreferences.user } returns flowOf(user)

        val result = repository.getUser()

        assertEquals(user, result)
        coVerify(exactly = 1) { userPreferences.user }
    }

    @Test
    fun `getUser returns null if no user`() = runTest {
        coEvery { userPreferences.user } returns flowOf(null)

        val result = repository.getUser()

        assertNull(result)
        coVerify(exactly = 1) { userPreferences.user }
    }

    @Test
    fun `saveUser calls userPreferences saveUser`() = runTest {
        coEvery { userPreferences.saveUser(user) } returns Unit

        repository.saveUser(user)

        coVerify(exactly = 1) { userPreferences.saveUser(user) }
    }
}
