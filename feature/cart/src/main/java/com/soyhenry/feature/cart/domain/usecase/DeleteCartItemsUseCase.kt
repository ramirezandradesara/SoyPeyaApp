package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class DeleteCartItemsUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke() {
        repository.deleteCartItems()
    }
}
