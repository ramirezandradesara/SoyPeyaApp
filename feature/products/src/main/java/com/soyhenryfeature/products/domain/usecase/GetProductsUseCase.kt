package com.soyhenryfeature.products.domain.usecase

import com.soyhenry.data.repository.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    suspend operator fun invoke(refreshData: Boolean) = productsRepository.getAllProducts(refreshData)
}