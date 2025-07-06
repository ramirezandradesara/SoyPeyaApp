package com.soyhenry.core.repository

import com.soyhenry.core.model.database.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDataSource @Inject constructor() {
    fun getAllProducts(): List<ProductEntity> {

        return listOf(
            ProductEntity(
                _id = "A1",
                name = "Pasta salad with cooked vegetables",
                price = 9.99,
                category = "Salad",
                imageUrl = "https://www.whereyougetyourprotein.com/wp-content/uploads/2024/04/vegan-pasta-salad-1.jpg",
                description = "A delicious pasta salad with a variety of cooked vegetables, perfect for a light meal or side dish.",
                hasDrink = true,
                _v = 0,
                createdAt = "2025-06-24T18:55:18.282Z",
                updatedAt = "2025-06-24T18:55:18.282Z"
            ),
            ProductEntity(
                _id = "A2",
                name =  "Cucumber salad",
                price = 9.99,
                category = "Salad",
                imageUrl = "https://sailorbailey.com/wp-content/uploads/2023/06/Spicy-Cucumber-Salad-1.jpg",
                description = "A refreshing cucumber salad with a tangy dressing, perfect for a summer meal.",
                hasDrink = true,
                _v = 0,
                createdAt = "2025-06-24T18:55:18.282Z",
                updatedAt = "2025-06-24T18:55:18.282Z"
            )
        )
    }
}
