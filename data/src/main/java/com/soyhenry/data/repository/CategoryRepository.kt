package com.soyhenry.data.repository

import com.soyhenry.core.domain.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}