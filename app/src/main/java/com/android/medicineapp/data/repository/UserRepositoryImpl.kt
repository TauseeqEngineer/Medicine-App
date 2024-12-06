package com.android.medicineapp.data.repository

import com.android.medicineapp.data.local.UserDao
import com.android.medicineapp.data.model.User
import com.android.medicineapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {
    override suspend fun getLoggedInUser(): User? = dao.getLoggedInUser()

    override suspend fun saveUser(identifier: String) {
        dao.saveUser(User(identifier))
    }

    override suspend fun logout() {
        dao.logout()
    }

}

