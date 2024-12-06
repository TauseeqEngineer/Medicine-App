package com.android.medicineapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.domain.repository.MedicineRepository
import com.android.medicineapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    // Define the UI state
    private val _uiState = MutableStateFlow<UIState<List<Medicine>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Medicine>>> = _uiState

    init {
        loadMedicines()
    }

    private fun loadMedicines() {
        // Simulate network call or load from repository
        viewModelScope.launch {
            _uiState.value = UIState.Loading // Set loading state
            try {
                // Collect the Flow and pass the list of medicines to the success state
                repository.getMedicines().collect { medicines ->
                    _uiState.value = UIState.Success(medicines) // Set success state with actual data
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Failed to load data: ${e.message}") // Set error state
            }
        }
    }
}


