package com.soyhenry.data.local.datasource

import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.core.model.entities.CartItemWithProductEntity
import kotlinx.coroutines.flow.Flow

interface CartItemLocalDataSource {
    suspend fun insertCartItem(cartItem: CartItemEntity)
    suspend fun updateCartItem(cartItem: CartItemEntity)
    suspend fun deleteCartItems()
    suspend fun deleteCartItem(cartItemId: Int)
    fun getAllCartItems(): Flow<List<CartItemEntity>>
    fun getAllCartItemsWithProducts(): Flow<List<CartItemWithProductEntity>>
    suspend fun getCartItemByProductId(productId: String): CartItemEntity?
}
