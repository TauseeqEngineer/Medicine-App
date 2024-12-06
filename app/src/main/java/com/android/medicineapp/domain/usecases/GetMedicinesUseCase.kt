package com.android.medicineapp.domain.usecases

import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// UseCase class for fetching the list of medicines
class GetMedicinesUseCase @Inject constructor(
    private val repository: MedicineRepository // Injecting the MedicineRepository
) {
    // The invoke operator allows this class to be called as a function.
    suspend operator fun invoke(): Flow<List<Medicine>> = repository.getMedicines()
}
