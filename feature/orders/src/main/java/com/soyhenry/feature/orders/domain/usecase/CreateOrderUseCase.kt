package com.soyhenry.feature.orders.domain.usecase

import com.soyhenry.data.remote.dto.OrderDto
import com.soyhenry.data.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: OrderDto) {
        repository.createOrder(order)
    }
}
