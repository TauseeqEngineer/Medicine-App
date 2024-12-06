package com.android.medicineapp

import com.android.medicineapp.data.model.Medicine
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.*

class MedicineRepositoryTest {

    private val apiService: ApiService = mock()  // Mocking the ApiService
    private val medicineRepository = MedicineRepository(apiService)

    @Test
    fun `fetchMedicines returns correct data`(): Unit = runBlocking {
        val expectedMedicines = listOf(
            Medicine("Aspirin", "500mg", "Pain Relief"),
            Medicine("Paracetamol", "250mg", "Fever Reduction")
        )

        whenever(apiService.getMedicines()).thenReturn(expectedMedicines)

        val actualMedicines = medicineRepository.fetchMedicines()

        assertEquals(expectedMedicines, actualMedicines)  // Check if the fetched data is correct
        verify(apiService).getMedicines()  // Verify that the API call was made
    }

    @Test
    fun `getMedicineByName returns the correct medicine`(): Unit = runBlocking {
        val expectedMedicines = listOf(
            Medicine("Aspirin", "500mg", "Pain Relief"),
            Medicine("Paracetamol", "250mg", "Fever Reduction")
        )
        whenever(apiService.getMedicines()).thenReturn(expectedMedicines)

        val actualMedicine = medicineRepository.getMedicineByName("Aspirin")

        // Assert: Verify that the correct medicine was returned
        assertEquals(Medicine("Aspirin", "500mg", "Pain Relief"), actualMedicine)
        verify(apiService).getMedicines()
    }
}

class MedicineRepository(private val apiService: ApiService) {
    suspend fun fetchMedicines(): List<Medicine> {
        return apiService.getMedicines()
    }

    suspend fun getMedicineByName(name: String): Medicine? {
        return apiService.getMedicines().find { it.name == name }
    }
}


interface ApiService {
    suspend fun getMedicines(): List<Medicine>

}



