package com.android.medicineapp.domain.repository

import com.android.medicineapp.data.model.User

// Interface for managing user-related actions in a repository
interface UserRepository {

    // Returns a User object if the user is logged in, or null if not
    suspend fun getLoggedInUser(): User?

    // The identifier could be a username, email, or any unique key
    suspend fun saveUser(identifier: String)

    // This should clear user-related data and mark the user as logged out
    suspend fun logout()
}
