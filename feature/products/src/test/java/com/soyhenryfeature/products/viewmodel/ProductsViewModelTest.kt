package com.soyhenryfeature.products.viewmodel

import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import com.soyhenry.core.model.domain.Category
import com.soyhenry.core.model.domain.Product
import com.soyhenry.core.model.state.UiState
import com.soyhenryfeature.products.MainDispatcherRule
import com.soyhenryfeature.products.domain.usecase.GetCategoriesUseCase
import com.soyhenryfeature.products.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.coEvery
import io.mockk.mockk

@ExperimentalComposeRuntimeApi
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var viewModel: ProductsViewModel

    private val fakeProducts = listOf(
        Product(
            id = "1",
            name = "Salsa Roja",
            description = "Hecha con tomates frescos",
            imgURL = "",
            price = 12.0,
            category = ""
        ),
        Product(
            id = "2",
            name = "Salsa Verde",
            description = "Hecha con albahaca fresca",
            imgURL = "",
            price = 15.0,
            category = ""
        )
    )

    @Before
    fun setup() {
        getProductsUseCase = mockk()
        getCategoriesUseCase = mockk()
    }

    @Test
    fun `loadProducts sets UiState to Success with product list`() = runTest {
        coEvery { getProductsUseCase(any()) } returns fakeProducts

        viewModel = ProductsViewModel(getProductsUseCase, getCategoriesUseCase)

        viewModel.loadProducts()

        assert(viewModel.uiState.value is UiState.Success)
        val state = viewModel.uiState.value as UiState.Success
        assert(state.data == fakeProducts)
    }

    @Test
    fun `onFilterTextChange filters products correctly`() = runTest {
        coEvery { getProductsUseCase(any()) } returns fakeProducts

        viewModel = ProductsViewModel(getProductsUseCase, getCategoriesUseCase)

        viewModel.loadProducts()

        viewModel.onFilterTextChange("Roja")

        val state = viewModel.uiState.value as UiState.Success
        assert(state.data.size == 1)
        assert(state.data[0].name == "Salsa Roja")
    }

    @Test
    fun `loadProducts sets UiState to Error when exception is thrown`() = runTest {
        coEvery { getProductsUseCase(any()) } throws Exception("Network error")

        viewModel = ProductsViewModel(getProductsUseCase, getCategoriesUseCase)

        viewModel.loadProducts()

        val state = viewModel.uiState.value
        assert(state is UiState.Error)
        assert((state as UiState.Error).message == "Network error")
    }

    @Test
    fun `loadCategories sets UiState to Success with categories list`() = runTest {
        val fakeCategories = listOf(
            Category("Burger", "burger"),
            Category("Pizza", "pizza")
        )

        coEvery { getCategoriesUseCase() } returns fakeCategories

        viewModel = ProductsViewModel(getProductsUseCase, getCategoriesUseCase)

        viewModel.loadCategories()

        val state = viewModel.categoriesState.value
        assert(state is UiState.Success)
        val successState = state as UiState.Success
        assert(successState.data.size == fakeCategories.size)
        assert(successState.data[0].label == fakeCategories[0].label)
    }

    @Test
    fun `loadCategories sets UiState to Error when exception is thrown`() = runTest {
        coEvery { getCategoriesUseCase() } throws Exception("Error al cargar categorías")

        viewModel = ProductsViewModel(getProductsUseCase, getCategoriesUseCase)

        viewModel.loadCategories()

        val state = viewModel.categoriesState.value
        assert(state is UiState.Error)
        assert((state as UiState.Error).message == "Error al cargar categorías")
    }

}
