package com.soyhenry.feature.cart.domain.usecase

import com.soyhenry.data.repository.CartItemRepository
import javax.inject.Inject

class GetAllCartItemsWithProductsUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository
){
    operator fun invoke() = cartItemRepository.getAllCartItemsWithProducts()
}