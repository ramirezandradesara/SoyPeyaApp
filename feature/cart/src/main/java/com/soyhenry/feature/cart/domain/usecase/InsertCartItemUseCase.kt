package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class InsertCartItemUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke(cartItem: CartItemEntity) {
        repository.insertCartItem(cartItem)
    }
}
