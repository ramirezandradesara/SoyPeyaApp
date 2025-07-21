package com.soyhenry.data.repository

import com.soyhenry.data.local.datasource.OrderLocalDataSource
import com.soyhenry.data.mappers.toOrderEntity
import com.soyhenry.data.mappers.toOrderItemEntities
import com.soyhenry.data.mappers.toDomain
import com.soyhenry.data.remote.datasource.OrderRemoteDataSource
import com.soyhenry.data.remote.dto.CartItemDto
import com.soyhenry.data.remote.dto.OrderDto
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class OrderRepositoryImplTest {

    private val remote: OrderRemoteDataSource = mockk()
    private val local: OrderLocalDataSource = mockk()
    private lateinit var repository: OrderRepository

    private val orderDto = OrderDto(
        orderId = "123",
        productIds = listOf(CartItemDto(
            productId = "p1",
            name = "Producto 1",
            description = "",
            imageUrl = "",
            price = 20.00,
            hasDrink = true,
            quantity = 1
        )),
        total = 200.00,
        timestamp = 1234567890L
    )

    private val orderEntity = orderDto.toOrderEntity()
    private val orderItemEntities = orderDto.toOrderItemEntities()
    private val domainOrder = orderDto.toDomain()

    @Before
    fun setup() {
        repository = OrderRepositoryImpl(remote, local)
    }

    @Test
    fun `createOrder calls remote datasource`() = runTest {
        coEvery { remote.createOrder(orderDto) } just Runs

        repository.createOrder(orderDto)

        coVerify { remote.createOrder(orderDto) }
    }

    @Test
    fun `getOrders refreshData true - returns remote orders and updates local`() = runTest {
        coEvery { remote.getOrders() } returns listOf(orderDto)
        coEvery { local.updateOrders(listOf(orderEntity), orderItemEntities) } just Runs

        val result = repository.getOrders(refreshData = true)

        assertEquals(listOf(domainOrder), result)
        coVerifySequence {
            remote.getOrders()
            local.updateOrders(listOf(orderEntity), orderItemEntities)
        }
    }

    @Test
    fun `getOrders refreshData true - remote fails, fallback to local`() = runTest {
        coEvery { remote.getOrders() } throws RuntimeException("Network error")
        coEvery { local.getOrders() } returns listOf(orderEntity)

        val result = repository.getOrders(refreshData = true)

        assertEquals(listOf(domainOrder), result)
        coVerify { local.getOrders() }
    }

    @Test
    fun `getOrders refreshData false - local has data`() = runTest {
        coEvery { local.getOrders() } returns listOf(orderEntity)

        val result = repository.getOrders(refreshData = false)

        assertEquals(listOf(domainOrder), result)
        coVerify(exactly = 0) { remote.getOrders() }
    }

    @Test
    fun `getOrders refreshData false - local empty, fallback to remote`() = runTest {
        coEvery { local.getOrders() } returns emptyList()
        coEvery { remote.getOrders() } returns listOf(orderDto)
        coEvery { local.updateOrders(listOf(orderEntity), orderItemEntities) } just Runs

        val result = repository.getOrders(refreshData = false)

        assertEquals(listOf(domainOrder), result)
        coVerifySequence {
            local.getOrders()
            remote.getOrders()
            local.updateOrders(listOf(orderEntity), orderItemEntities)
        }
    }

    @Test
    fun `getOrders refreshData false - both fail throws exception`() = runTest {
        coEvery { local.getOrders() } returns emptyList()
        coEvery { remote.getOrders() } throws RuntimeException("No internet")

        assertFailsWith<RuntimeException> {
            repository.getOrders(refreshData = false)
        }
    }
}
