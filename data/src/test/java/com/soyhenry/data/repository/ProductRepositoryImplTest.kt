package com.soyhenry.data.repository

import com.soyhenry.core.domain.Product
import com.soyhenry.data.local.datasource.ProductLocalDataSource
import com.soyhenry.data.remote.datasource.ProductRemoteDataSource
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.mappers.toEntity
import com.soyhenry.data.remote.dto.ProductDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    private val remote: ProductRemoteDataSource = mockk()
    private val local: ProductLocalDataSource = mockk()
    private lateinit var repository: ProductRepositoryImpl

    private val sampleProductDto = ProductDto(
        id = "p1",
        name = "Producto 1",
        description = "",
        imageUrl = "",
        price = 12.00,
        hasDrink = false,
        category = ""
    )
    private val sampleProductEntity = sampleProductDto.toEntity()
    private val sampleProductDomain = sampleProductDto.toDomain()

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(remote, local)
    }

    @Test
    fun `getAllProducts refreshData true - remote success updates local and returns remote mapped`() = runTest {
        coEvery { remote.getAllProducts() } returns listOf(sampleProductDto)
        coEvery { local.updateProducts(any()) } returns Unit

        val result = repository.getAllProducts(refreshData = true)

        assertEquals(listOf(sampleProductDomain), result)
        coVerify(exactly = 1) { remote.getAllProducts() }
        coVerify(exactly = 1) { local.updateProducts(listOf(sampleProductEntity)) }
    }

    @Test
    fun `getAllProducts refreshData true - remote fails fallback local with data`() = runTest {
        coEvery { remote.getAllProducts() } throws RuntimeException("Remote error")
        coEvery { local.getAllProducts() } returns listOf(sampleProductEntity)

        val result = repository.getAllProducts(refreshData = true)

        assertEquals(listOf(sampleProductDomain), result)
        coVerify(exactly = 1) { local.getAllProducts() }
    }

    @Test
    fun `getAllProducts refreshData true - remote fails fallback local empty throws`() = runTest {
        coEvery { remote.getAllProducts() } throws RuntimeException("Remote error")
        coEvery { local.getAllProducts() } returns emptyList()

        assertFailsWith<RuntimeException> {
            repository.getAllProducts(refreshData = true)
        }
    }

    @Test
    fun `getAllProducts refreshData false - local has data returns local mapped`() = runTest {
        coEvery { local.getAllProducts() } returns listOf(sampleProductEntity)

        val result = repository.getAllProducts(refreshData = false)

        assertEquals(listOf(sampleProductDomain), result)
        coVerify(exactly = 0) { remote.getAllProducts() }
    }

    @Test
    fun `getAllProducts refreshData false - local empty fallback remote inserts local and returns remote mapped`() = runTest {
        coEvery { local.getAllProducts() } returns emptyList()
        coEvery { remote.getAllProducts() } returns listOf(sampleProductDto)
        coEvery { local.insertProducts(any()) } returns Unit

        val result = repository.getAllProducts(refreshData = false)

        assertEquals(listOf(sampleProductDomain), result)
        coVerifySequence {
            local.getAllProducts()
            remote.getAllProducts()
            local.insertProducts(listOf(sampleProductEntity))
        }
    }

    @Test
    fun `getAllProducts refreshData false - local empty fallback remote throws`() = runTest {
        coEvery { local.getAllProducts() } returns emptyList()
        coEvery { remote.getAllProducts() } throws RuntimeException("Remote error")

        assertFailsWith<RuntimeException> {
            repository.getAllProducts(refreshData = false)
        }
    }
}
