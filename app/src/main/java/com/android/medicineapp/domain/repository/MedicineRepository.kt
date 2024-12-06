package com.android.medicineapp.domain.repository

import com.android.medicineapp.data.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {
    suspend fun getMedicines(): Flow<List<Medicine>>
}
