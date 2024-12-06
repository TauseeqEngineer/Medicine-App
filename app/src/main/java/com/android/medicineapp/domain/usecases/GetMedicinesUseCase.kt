package com.android.medicineapp.domain.usecases

import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicinesUseCase @Inject constructor(
    private val repository: MedicineRepository
) {
    suspend operator fun invoke(): Flow<List<Medicine>> = repository.getMedicines()
}
