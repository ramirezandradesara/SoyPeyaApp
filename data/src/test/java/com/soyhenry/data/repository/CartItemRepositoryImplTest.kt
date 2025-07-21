package com.soyhenry.data.repository

import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.core.model.entities.CartItemWithProductEntity
import com.soyhenry.core.model.entities.ProductEntity
import com.soyhenry.core.model.mappers.toDomain
import com.soyhenry.data.local.datasource.CartItemLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class CartItemRepositoryImplTest {

    private lateinit var local: CartItemLocalDataSource
    private lateinit var repository: CartItemRepositoryImpl

    private val cartItemEntity = CartItemEntity(
        id = 1,
        productId = "p1",
        quantity = 2,
    )

    @Before
    fun setUp() {
        local = mockk(relaxed = true)
        repository = CartItemRepositoryImpl(local)
    }

    @Test
    fun `insertCartItem calls local insertCartItem`() = runTest {
        repository.insertCartItem(cartItemEntity)

        coVerify { local.insertCartItem(cartItemEntity) }
    }

    @Test
    fun `updateCartItem calls local updateCartItem`() = runTest {
        repository.updateCartItem(cartItemEntity)

        coVerify { local.updateCartItem(cartItemEntity) }
    }

    @Test
    fun `deleteCartItems calls local deleteCartItems`() = runTest {
        repository.deleteCartItems()

        coVerify { local.deleteCartItems() }
    }

    @Test
    fun `deleteCartItem calls local deleteCartItem`() = runTest {
        repository.deleteCartItem(1)

        coVerify { local.deleteCartItem(1) }
    }

    @Test
    fun `getAllCartItems returns flow of cart item entities`() = runTest {
        val expected = listOf(cartItemEntity)
        every { local.getAllCartItems() } returns flowOf(expected)

        val result = repository.getAllCartItems()

        result.collect { items ->
            assertEquals(expected, items)
        }
    }

    @Test
    fun `getAllCartItemsWithProducts returns mapped domain items`() = runTest {
        val localList = listOf(
            CartItemWithProductEntity(
                cartItem = CartItemEntity(
                    id = 0,
                    productId = "1",
                    quantity = 2
                ),
                product = ProductEntity(
                    id = "p1",
                    productName = "Mock Product",
                    price = 10.0,
                    imageURL = "",
                    category = "",
                    isFeatured = false
                )
            )
        )

        every { local.getAllCartItemsWithProducts() } returns flowOf(localList)

        val result = repository.getAllCartItemsWithProducts()

        result.collect { items ->
            val expected = localList.map { it.toDomain() }
            assertEquals(expected, items)
        }
    }


    @Test
    fun `getCartItemByProductId returns expected item`() = runTest {
        coEvery { local.getCartItemByProductId("p1") } returns cartItemEntity

        val result = repository.getCartItemByProductId("p1")

        assertEquals(cartItemEntity, result)
    }
}
