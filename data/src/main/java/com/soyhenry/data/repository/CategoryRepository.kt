package com.soyhenry.data.repository

import com.soyhenry.core.model.domain.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}