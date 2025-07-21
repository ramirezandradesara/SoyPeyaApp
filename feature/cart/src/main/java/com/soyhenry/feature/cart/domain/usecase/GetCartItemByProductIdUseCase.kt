package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.core.model.entities.CartItemEntity
import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class GetCartItemByProductIdUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke(productId: String): CartItemEntity? {
        return repository.getCartItemByProductId(productId)
    }
}
