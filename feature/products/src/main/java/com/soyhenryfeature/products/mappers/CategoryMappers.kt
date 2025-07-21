package com.soyhenryfeature.products.mappers

import com.soyhenry.core.model.domain.Category
import com.soyhenryfeature.products.model.UiCategory
import com.soyhenryfeature.products.R

fun Category.toUiCategory(): UiCategory {
    val imageRes = when (value) {
        "Burger" -> R.drawable.burger
        "Pizza" -> R.drawable.pizza
        "Taco" -> R.drawable.taco
        "Salad" -> R.drawable.salad
        "Hot dog" -> R.drawable.hot_dog
        else -> R.drawable.todo
    }
    return UiCategory(label, value, imageRes)
}
