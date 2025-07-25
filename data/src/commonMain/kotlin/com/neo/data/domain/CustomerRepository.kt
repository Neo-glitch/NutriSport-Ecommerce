package com.neo.data.domain

import dev.gitlive.firebase.auth.FirebaseUser
import com.neo.shared.domain.CartItem
import com.neo.shared.domain.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCurrentUserId(): String?
    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    )

//    fun readCustomerFlow(): Flow<RequestState<Customer>>
//    suspend fun updateCustomer(
//        customer: Customer,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    )
//
//    suspend fun addItemToCard(
//        cartItem: CartItem,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    )
//
//    suspend fun updateCartItemQuantity(
//        id: String,
//        quantity: Int,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    )
//
//    suspend fun deleteCartItem(
//        id: String,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    )
//
//    suspend fun deleteAllCartItems(
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    )
//
//    suspend fun signOut(): RequestState<Unit>
}