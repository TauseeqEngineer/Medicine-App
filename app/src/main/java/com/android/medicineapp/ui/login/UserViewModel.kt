package com.android.medicineapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.medicineapp.data.model.User
import com.android.medicineapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            _user.value = userRepository.getLoggedInUser()
        }
    }

    fun saveUser(identifier: String) {
        viewModelScope.launch {
            userRepository.saveUser(identifier)
            _user.value = User(identifier)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _user.value = null
        }
    }
}
