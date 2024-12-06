package com.android.medicineapp.domain.repository

import com.android.medicineapp.data.model.User

interface UserRepository {
    suspend fun getLoggedInUser(): User?
    suspend fun saveUser(identifier: String)
    suspend fun logout()
}