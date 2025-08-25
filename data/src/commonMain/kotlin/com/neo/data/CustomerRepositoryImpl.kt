package com.neo.data

import com.neo.data.domain.CustomerRepository
import com.neo.shared.domain.Customer
import com.neo.shared.util.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.cancellation.CancellationException

class CustomerRepositoryImpl : CustomerRepository {
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
            if (e is CancellationException) throw e
            onError("Error while creating a customer: ${e.message}")
        }
    }

    override suspend fun signOut(): RequestState<Unit> {
        return try {
            Firebase.auth.signOut()
            RequestState.Success(data = Unit)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            RequestState.Error("Error while signing out: ${e.message}")
        }
    }

    override fun readCustomerFlow(): Flow<RequestState<Customer>> {
        return channelFlow {
            try {
                val userId = getCurrentUserId()
                if (userId != null) {
                    val database = Firebase.firestore
                    val customerCollection = database.collection(collectionPath = "customers")
                    customerCollection.document(userId).snapshots.collectLatest { document ->
                        if (document.exists) {
                            val customer = Customer(
                                id = document.id,
                                firstName = document.get(field = "firstName"),
                                lastName = document.get(field = "lastName"),
                                email = document.get(field = "email"),
                                city = document.get(field = "city"),
                                postalCode = document.get(field = "postalCode"),
                                address = document.get(field = "address"),
                                phoneNumber = document.get(field = "phoneNumber"),
                                cart = document.get(field = "cart"),
                            )
                            send(RequestState.Success(data = customer))
                        } else {
                            send(RequestState.Error("Customer document does not exist."))
                        }
                    }

                } else {
                    send(RequestState.Error("User is not authenticated."))
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                send(RequestState.Error("Error while reading customer information: ${e.message}"))

            }
        }
    }


    override suspend fun updateCustomer(
        customer: Customer,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        try {
            val userId = getCurrentUserId()
            if (userId != null) {
                val database = Firebase.firestore
                val customerCollection = database.collection(collectionPath = "customers")

                val existingCustomer = customerCollection.document(customer.id).get()

                if (existingCustomer.exists) {
                    customerCollection.document(customer.id)
                        .update(
                            "firstName" to customer.firstName,
                            "lastName" to customer.lastName,
                            "city" to customer.city,
                            "postalCode" to customer.postalCode,
                            "address" to customer.address,
                            "phoneNumber" to customer.phoneNumber
                        )
                    onSuccess()
                } else {
                    onError("Customer not found.")
                }
            } else {
                onError("User is not authenticated.")
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            onError("Error while updating customer information: ${e.message}")
        }
    }
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