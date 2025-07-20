package com.soyhenry.data.repository

import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartItemRepository {
    suspend fun insertCartItem(cartItem: CartItemEntity)
    suspend fun updateCartItem(cartItem: CartItemEntity)
    suspend fun deleteCartItems()
    suspend fun deleteCartItem(cartItemId: Int)
    fun getAllCartItems(): Flow<List<CartItemEntity>>
    fun getAllCartItemsWithProducts(): Flow<List<CartItem>>
    suspend fun getCartItemByProductId(productId: String): CartItemEntity?
}