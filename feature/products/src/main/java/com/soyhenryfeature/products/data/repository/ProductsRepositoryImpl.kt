package com.soyhenryfeature.products.data.repository

import com.soyhenry.core.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject constructor() : ProductsRepository {
    private val _products = mutableListOf(
        Product(
            1,
            "Vegan Pasta Salad",
            9.99,
            "Pasta salad with cooked vegetables",
            imgURL = "https://www.whereyougetyourprotein.com/wp-content/uploads/2024/04/vegan-pasta-salad-1.jpg"
        ),
        Product(
            2,
            "Yummy salad",
            7.99,
            "A delicious and fresh salad",
            imgURL = "https://images.immediate.co.uk/production/volatile/sites/30/2014/05/Epic-summer-salad-hub-2646e6e.jpg"
        ),
        Product(
            3,
            "Cucumber salad",
            20.99,
            "A delicious and fresh cucumber salad",
            imgURL = "https://sailorbailey.com/wp-content/uploads/2023/06/Spicy-Cucumber-Salad-1.jpg"
        ),
        Product(
            4,
            "Salad bowl premium",
            20.99,
            "A delicious and fresh cucumber salad",
            imgURL = ""
        )
    )

    override suspend fun getProducts(): List<Product> {
        delay(500)
        return _products.toList()
    }

    override suspend fun addProduct(product: Product) {
        _products.add(product)
    }

    override suspend fun deleteProduct(productId: Int) {
        _products.removeIf { it.id == productId }
    }
}