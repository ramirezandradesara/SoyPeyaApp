package com.soyhenryfeature.products.viewmodel

import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import com.soyhenry.core.model.database.entities.ProductEntity
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
// import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlin.math.exp

@ExperimentalComposeRuntimeApi
class ProductsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainsDispatcherRule

    private val getProductsUseCase: GetProductsUserCase = mokk()
    private val updateProductsUseCase: UpdateProductsUseCase = mockk()

    @Before
    fun setup(){
        coEvery { updateProductsUseCase.invoke } returns Uni
    }

    @Test
    fun `all products emits expected products`(){
        // Arrenge
        val expectedProducts = listOf(
            ProductEntity(
                id = "prod1",
                productName = "",
                price= 10.2,
                imageURL = "https",
                isFeatured = false,
                category = "salsa"
            )
        )

        val flow = flowOf(expectedProducts)

        every{ getProductsUseCase.invoke() } return true

        val viewmodel = ProductsViewModel(
            getProductsUseCase = getProductsUseCase,
            updateProductsUseCase = updateProductsUseCase
        )

        // Act
        val result = viewmodel.allProducts.first()

        //asset
        assert(result == expectedProducts)
    }
}