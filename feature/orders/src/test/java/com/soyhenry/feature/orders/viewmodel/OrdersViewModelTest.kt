package com.soyhenry.feature.orders.viewmodel

import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.domain.Order
import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.state.UiState
import com.soyhenry.feature.orders.MainDispatcherRule
import com.soyhenry.feature.orders.domain.usecase.CreateOrderUseCase
import com.soyhenry.feature.orders.domain.usecase.GetOrdersUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

@ExperimentalCoroutinesApi
class OrdersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeProduct1 = Product(
        id = "p1",
        name = "Producto 1",
        price = 10.0,
        imgURL = "https://image1.jpg",
        description = "",
        category = ""
    )

    private val fakeProduct2 = Product(
        id = "p2",
        name = "Producto 2",
        price = 5.0,
        imgURL = "https://image2.jpg",
        description = "",
        category = ""
    )

    private val fakeCartItem1 = CartItem(
        id = 1,
        productId = "p1",
        product = fakeProduct1,
        quantity = 2
    )

    private val fakeCartItem2 = CartItem(
        id = 2,
        productId = "p2",
        product = fakeProduct2,
        quantity = 1,
    )

    private val fakeOrder1 = Order(
        id = "order1",
        totalAmount = fakeCartItem1.quantity * fakeCartItem1.product.price +
                fakeCartItem2.quantity * fakeCartItem2.product.price,
        date = 1690176000000L,
        totalItems = 2
    )

    private val fakeOrdersList = listOf(fakeOrder1)

    private lateinit var viewModel: OrdersViewModel
    private lateinit var createOrderUseCase: CreateOrderUseCase
    private lateinit var getOrdersUseCase: GetOrdersUseCase

    @Before
    fun setUp() {
        createOrderUseCase = mockk()
        getOrdersUseCase = mockk()
        viewModel = OrdersViewModel(createOrderUseCase, getOrdersUseCase)
    }

    @Test
    fun `loadOrders emits Success when use case returns data`() = runTest {
        coEvery { getOrdersUseCase(any()) } returns fakeOrdersList

        viewModel.loadOrders()

        val state = viewModel.uiState.first()
        assertTrue(state is UiState.Success)
        assertEquals(fakeOrdersList, (state as UiState.Success).data)
    }

    @Test
    fun `loadOrders emits Error when use case throws exception`() = runTest {
        coEvery { getOrdersUseCase(any()) } throws RuntimeException("Error al cargar órdenes")

        viewModel.loadOrders()

        val state = viewModel.uiState.first()
        assertTrue(state is UiState.Error)
        assertEquals("Error al cargar órdenes", (state as UiState.Error).message)
    }

    @Test
    fun `createOrder invokes use case and calls onSuccess`() = runTest {
        coEvery { createOrderUseCase(any()) } just Runs

        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        val cartItems = listOf(fakeCartItem1, fakeCartItem2)

        viewModel.createOrder(cartItems, onSuccess, onError)

        coVerify(exactly = 1) { createOrderUseCase(any()) }
        verify { onSuccess() }
        verify(exactly = 0) { onError(any()) }
    }

    @Test
    fun `createOrder calls onError when use case fails`() = runTest {
        val errorMsg = "Error al crear orden"
        coEvery { createOrderUseCase(any()) } throws RuntimeException(errorMsg)

        val cartItems = listOf(
            CartItem(
                id = 1,
                productId = "p1",
                product = Product("p1", "Producto 1", "", "", 10.0, ""),
                quantity = 2
            )
        )
        val onSuccess = mockk<() -> Unit>(relaxed = true)
        val onError = mockk<(String) -> Unit>(relaxed = true)

        viewModel.createOrder(cartItems, onSuccess, onError)

        advanceUntilIdle()

        coVerify { createOrderUseCase(any()) }
        verify { onError(errorMsg) }
        verify(exactly = 0) { onSuccess() }
    }
}
