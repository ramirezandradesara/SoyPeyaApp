package com.soyhenry.data.local.datasource

import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.core.model.entities.CartItemWithProductEntity
import com.soyhenry.data.local.dao.CartItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemLocalDataSourceImpl @Inject constructor(
    private val cartItemDao: CartItemDao
) : CartItemLocalDataSource {

    override suspend fun insertCartItem(cartItem: CartItemEntity) {
        cartItemDao.insertCartItem(cartItem)
    }

    override suspend fun updateCartItem(cartItem: CartItemEntity) {
        cartItemDao.updateCartItem(cartItem)
    }

    override suspend fun deleteCartItems() {
        cartItemDao.deleteCartItems()
    }

    override suspend fun deleteCartItem(cartItemId: Int) {
        cartItemDao.deleteCartItem(cartItemId)
    }

    override fun getAllCartItems(): Flow<List<CartItemEntity>> {
        return cartItemDao.getAllCartItems()
    }

    override fun getAllCartItemsWithProducts(): Flow<List<CartItemWithProductEntity>> {
        return cartItemDao.getCartItemsWithProducts()
    }

    override suspend fun getCartItemByProductId(productId: String): CartItemEntity? {
        return cartItemDao.getCartItemByProductId(productId)
    }
}
