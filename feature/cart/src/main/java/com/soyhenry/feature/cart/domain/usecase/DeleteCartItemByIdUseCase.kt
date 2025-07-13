package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class DeleteCartItemByIdUseCase @Inject constructor(
    private val repository: CartItemRepository
) {
    suspend operator fun invoke(cartItemId: Int) {
        repository.deleteCartItem(cartItemId)
    }
}
