package com.soyhenry.data.repository

import com.soyhenry.core.model.domain.Category

class CategoryRepositoryImpl : CategoryRepository {
    override suspend fun getCategories(): List<Category> = listOf(
        Category("Todos", null),
        Category("Burger", "Burger"),
        Category("Pizza", "Pizza"),
        Category("Taco", "Taco"),
        Category("Ensalada", "Salad"),
        Category("Hot dog", "Hot dog")
    )
}