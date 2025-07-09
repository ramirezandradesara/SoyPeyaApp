package com.soyhenry.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.soyhenry.core.domain.Product
import com.soyhenry.core.entities.ProductEntity

data class ProductDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String = "General",
    val imageUrl: String,
    val price: Double = 0.0,
    val hasDrink: Boolean = false,
    val category: String = ""
)