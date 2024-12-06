package com.android.medicineapp

import com.android.medicineapp.data.local.UserDao
import com.android.medicineapp.data.model.User

class InMemoryUserDao : UserDao {
    private val users = mutableListOf<User>()

    override suspend fun saveUser(user: User) {
        users.add(user)
    }

    override suspend fun logout() {
        users.clear()
    }

    override suspend fun getLoggedInUser(): User? {
        return users.firstOrNull()
    }

}
