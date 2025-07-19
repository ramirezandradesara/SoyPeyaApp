package com.soyhenry.data.repository

import com.soyhenry.core.domain.Category
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryRepositoryImplTest {

    private val repository = CategoryRepositoryImpl()

    @Test
    fun `getCategories returns predefined list`() = runBlocking {
        val expected = listOf(
            Category("Todos", null),
            Category("Burger", "Burger"),
            Category("Pizza", "Pizza"),
            Category("Taco", "Taco"),
            Category("Ensalada", "Salad"),
            Category("Hot dog", "Hot dog")
        )

        val result = repository.getCategories()

        assertEquals(expected, result)
    }
}
