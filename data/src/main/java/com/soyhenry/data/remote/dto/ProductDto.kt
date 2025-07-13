package com.soyhenry.data.remote.dto

import com.google.gson.annotations.SerializedName

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