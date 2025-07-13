package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.core.entities.CartItemEntity
import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke(cartItem: CartItemEntity) {
        repository.updateCartItem(cartItem)
    }
}
