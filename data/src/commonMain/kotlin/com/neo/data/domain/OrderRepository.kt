package com.neo.data.domain

import com.neo.shared.domain.Order

interface OrderRepository {
    fun getCurrentUserId(): String?
    suspend fun createTheOrder(
        order: Order,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}