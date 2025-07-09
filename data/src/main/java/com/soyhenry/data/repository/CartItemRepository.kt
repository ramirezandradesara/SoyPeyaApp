package com.soyhenry.data.repository

import com.soyhenry.core.entities.CartItemEntity
import com.soyhenry.core.entities.CartItemWithProductEntity
import com.soyhenry.data.local.dao.CartItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemRepository @Inject constructor(
    private val cartItemDao: CartItemDao
) {
    suspend fun insertCartItem(cartItem: CartItemEntity) {
        cartItemDao.insertCartItem(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItemEntity) {
        cartItemDao.updateCartItem(cartItem)
    }

    suspend fun deleteCartItems() {
        cartItemDao.deleteCartItems()
    }

    suspend fun deleteCartItem(cartItemId: Int) {
        cartItemDao.deleteCartItem(cartItemId)
    }

    fun getAllCartItems(): Flow<List<CartItemEntity>> {
        return cartItemDao.getAllCartItems()
    }

    fun getAllCartItemsWithProducts(): Flow<List<CartItemWithProductEntity>> {
        return cartItemDao.getCartItemsWithProducts()
    }

    suspend fun getCartItemByProductId(productId: String): CartItemEntity? {
        return cartItemDao.getCartItemByProductId(productId)
    }
}