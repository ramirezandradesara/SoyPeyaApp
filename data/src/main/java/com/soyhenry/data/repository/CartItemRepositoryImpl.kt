package com.soyhenry.data.repository

import com.soyhenry.core.entities.CartItemEntity
import com.soyhenry.core.entities.CartItemWithProductEntity
import com.soyhenry.data.local.datasource.CartItemLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val local: CartItemLocalDataSource
) : CartItemRepository {

    override suspend fun insertCartItem(cartItem: CartItemEntity) =
        local.insertCartItem(cartItem)

    override suspend fun updateCartItem(cartItem: CartItemEntity) =
        local.updateCartItem(cartItem)

    override suspend fun deleteCartItems() =
        local.deleteCartItems()

    override suspend fun deleteCartItem(cartItemId: Int) =
        local.deleteCartItem(cartItemId)

    override fun getAllCartItems(): Flow<List<CartItemEntity>> =
        local.getAllCartItems()

    override fun getAllCartItemsWithProducts(): Flow<List<CartItemWithProductEntity>> =
        local.getAllCartItemsWithProducts()

    override suspend fun getCartItemByProductId(productId: String): CartItemEntity? =
        local.getCartItemByProductId(productId)
}
