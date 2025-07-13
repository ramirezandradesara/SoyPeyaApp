package com.soyhenry.feature.orders.domain.usecase

import com.soyhenry.core.domain.Order
import com.soyhenry.data.repository.OrderRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(refreshData: Boolean = false): List<Order> {
        return repository.getOrders(refreshData)
    }
}
