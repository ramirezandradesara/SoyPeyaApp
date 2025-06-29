package com.soyhenry.core.repository

import com.soyhenry.core.model.database.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDataSource @Inject constructor() {
    fun getAllProducts(): List<ProductEntity> {

        return listOf(
            ProductEntity(
                id = "A1",
                productName =  "Pasta salad with cooked vegetables",
                price = 9.99,
                category = "Salad",
                imageURL = "https://www.whereyougetyourprotein.com/wp-content/uploads/2024/04/vegan-pasta-salad-1.jpg"
            ),
            ProductEntity(
                id = "A2",
                productName =  "Cucumber salad",
                price = 9.99,
                category = "Salad",
                imageURL = "https://sailorbailey.com/wp-content/uploads/2023/06/Spicy-Cucumber-Salad-1.jpg"
            )
        )
    }
}
