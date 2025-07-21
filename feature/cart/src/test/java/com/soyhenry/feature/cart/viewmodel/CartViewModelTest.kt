package com.soyhenry.feature.cart.viewmodel

import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.core.model.state.UiState
import com.soyhenry.feature.cart.MainDispatcherRule
import com.soyhenry.feature.cart.domain.usecase.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var insertCartItemUseCase: InsertCartItemUseCase
    private lateinit var getAllCartItemsWithProductsUseCase: GetAllCartItemsWithProductsUseCase
    private lateinit var getCartItemByProductIdUseCase: GetCartItemByProductIdUseCase
    private lateinit var updateCartItemUseCase: UpdateCartItemUseCase
    private lateinit var deleteCartItemByIdUseCase: DeleteCartItemByIdUseCase
    private lateinit var deleteCartItemsUseCase: DeleteCartItemsUseCase

    private lateinit var viewModel: CartViewModel

    private val product1 = Product(
        id = "p1",
        name = "Producto 1",
        price = 100.0,
        description = "Un producto aleatorio",
        imgURL = "",
        category = ""
    )

    private val cartItemEntity1 = CartItemEntity(
        id = 1,
        productId = "p1",
        quantity = 2)

    private val cartItem1 = CartItem(
        id = 1,
        productId = "p1",
        quantity = 2,
        product = product1
    )

    @Before
    fun setup() {
        insertCartItemUseCase = mockk(relaxed = true)
        getAllCartItemsWithProductsUseCase = mockk()
        getCartItemByProductIdUseCase = mockk()
        updateCartItemUseCase = mockk(relaxed = true)
        deleteCartItemByIdUseCase = mockk(relaxed = true)
        deleteCartItemsUseCase = mockk(relaxed = true)

        viewModel = CartViewModel(
            insertCartItemUseCase,
            getAllCartItemsWithProductsUseCase,
            getCartItemByProductIdUseCase,
            updateCartItemUseCase,
            deleteCartItemByIdUseCase,
            deleteCartItemsUseCase
        )
    }

    @Test
    fun `refreshCartItems emits loading and success`() = runTest {
        val cartItemsList = listOf(cartItem1)
        coEvery { getAllCartItemsWithProductsUseCase() } returns flow { emit(cartItemsList) }

        viewModel.refreshCartItems()

        assertTrue(viewModel.uiState.value is UiState.Success)
        val state = viewModel.uiState.value as UiState.Success
        assertEquals(cartItemsList, state.data)
    }

    @Test
    fun `refreshCartItems emits error on exception`() = runTest {
        coEvery { getAllCartItemsWithProductsUseCase() } throws RuntimeException("Database error")

        viewModel.refreshCartItems()

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertTrue((viewModel.uiState.value as UiState.Error).message.contains("Database error"))
    }

    @Test
    fun `updateQuantity updates existing cart item with clamped quantity`() = runTest {
        coEvery { getCartItemByProductIdUseCase("p1") } returns cartItemEntity1
        coEvery { insertCartItemUseCase(any()) } just Runs

        viewModel.updateQuantity("p1", 5)

        coVerify {
            insertCartItemUseCase(match { it.quantity == 5 })
        }

        viewModel.updateQuantity("p1", 0)

        coVerify {
            insertCartItemUseCase(match { it.quantity == 1 })
        }
    }

    @Test
    fun `addToCart updates quantity if item exists`() = runTest {
        coEvery { getCartItemByProductIdUseCase("p1") } returns cartItemEntity1
        coEvery { updateCartItemUseCase(any()) } just Runs
        coEvery { getAllCartItemsWithProductsUseCase() } returns flow { emit(emptyList()) }

        viewModel.addToCart(product1)

        coVerify {
            updateCartItemUseCase(match { it.quantity == cartItemEntity1.quantity + 1 })
        }
        coVerify { getAllCartItemsWithProductsUseCase() }
    }

    @Test
    fun `addToCart inserts new item if not exists`() = runTest {
        coEvery { getCartItemByProductIdUseCase("p1") } returns null
        coEvery { insertCartItemUseCase(any()) } just Runs
        coEvery { getAllCartItemsWithProductsUseCase() } returns flow { emit(emptyList()) }

        viewModel.addToCart(product1)

        coVerify {
            insertCartItemUseCase(match { it.productId == "p1" && it.quantity == 1 })
        }
        coVerify { getAllCartItemsWithProductsUseCase() }
    }

    @Test
    fun `removeFromCart deletes item and refreshes`() = runTest {
        coEvery { getCartItemByProductIdUseCase("p1") } returns cartItemEntity1
        coEvery { deleteCartItemByIdUseCase(1) } just Runs
        coEvery { getAllCartItemsWithProductsUseCase() } returns flow { emit(emptyList()) }

        viewModel.removeFromCart(cartItem1)

        coVerify { deleteCartItemByIdUseCase(1) }
        coVerify { getAllCartItemsWithProductsUseCase() }
    }

    @Test
    fun `removeAllFromCart deletes all and refreshes`() = runTest {
        coEvery { deleteCartItemsUseCase() } just Runs
        coEvery { getAllCartItemsWithProductsUseCase() } returns flow { emit(emptyList()) }

        viewModel.removeAllFromCart()

        coVerify { deleteCartItemsUseCase() }
        coVerify { getAllCartItemsWithProductsUseCase() }
    }
}
