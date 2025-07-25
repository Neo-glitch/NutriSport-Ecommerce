package com.neo.data.domain

import com.neo.shared.domain.CartItem
import com.neo.shared.domain.Customer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class CustomerRepositoryImpl: CustomerRepository {
    override fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(collectionPath = "customers")
                val customerObject = Customer(
                    id = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "Unknown",
                    lastName = user.displayName?.split(" ")?.lastOrNull() ?: "Unknown",
                    email = user.email ?: "Unknown",
                    cart = emptyList()
                )

                val customerExists = customerCollection.document(user.uid).get().exists

                if (customerExists) {
                    onSuccess()
                } else {
                    // create new customer
                    customerCollection.document(user.uid).set(customerObject)
                    onSuccess()
                }
            } else {
                onError("User is not available.")
            }
        } catch (e: Exception) {
            onError("Error while creating a customer: ${e.message}")
        }
    }
//
//    override suspend fun updateCustomer(
//        customer: Customer,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//    }
//
//    override suspend fun addItemToCard(
//        cartItem: CartItem,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//    }
//
//    override suspend fun updateCartItemQuantity(
//        id: String,
//        quantity: Int,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//    }
//
//    override suspend fun deleteCartItem(
//        id: String,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//    }
//
//    override suspend fun deleteAllCartItems(
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//    }
}