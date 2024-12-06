package com.android.medicineapp

import com.android.medicineapp.data.local.UserDao
import com.android.medicineapp.data.model.User
import com.android.medicineapp.data.repository.UserRepositoryImpl
import com.android.medicineapp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        userDao = InMemoryUserDao()
        userRepository = UserRepositoryImpl(userDao)
    }

    // Test case for saving a user
    @Test
    fun saveUser_savesCorrectly() = runBlocking {
        val user = User("Kaleem")

        userRepository.saveUser(user.identifier)

        val savedUser = userRepository.getLoggedInUser()
        assertEquals("Kaleem", savedUser?.identifier)
    }

    // Test case for logging out (deleting a user)
    @Test
    fun logout_deletesUser() = runBlocking {
        val user = User("Kaleem")
        userRepository.saveUser(user.identifier)

        userRepository.logout()

        val loggedOutUser = userRepository.getLoggedInUser()
        assertNull(loggedOutUser) // Assert that the user is null after logout
    }

    // Test case for getting the logged-in user
    @Test
    fun getLoggedInUser_returnsUser() = runBlocking {
        val user = User("Kaleem")
        userRepository.saveUser(user.identifier)

        val loggedInUser = userRepository.getLoggedInUser()

        assertNotNull(loggedInUser) // Assert that the logged-in user is not null
        assertEquals("Kaleem", loggedInUser?.identifier) // Assert that the correct user is fetched
    }
}
