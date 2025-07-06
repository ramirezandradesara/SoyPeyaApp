package com.soyhenryfeature.products.domain.usecase

import com.soyhenryfeature.products.data.repository.ProductsRepository
import javax.inject.Inject

class RefreshProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke() = productsRepository.refreshProducts()
}