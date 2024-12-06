package com.android.medicineapp.data.repository

import com.android.medicineapp.data.local.MedicineDao
import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.data.remote.api.ApiService
import com.android.medicineapp.domain.repository.MedicineRepository
import com.android.medicineapp.utils.recordLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedicineRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: MedicineDao
) : MedicineRepository {

    override suspend fun getMedicines(): Flow<List<Medicine>> {
        return flow {
            // Fetch from API
            val response = api.getMedicines()
            // Parse and map the API response to the Medicine model
            val medicines = response.problems.flatMap { problem ->
                problem.Diabetes?.flatMap { diabetes ->
                    diabetes.medications?.flatMap { medication ->
                        medication.medicationsClasses?.flatMap { classItem ->
                            val classNameDrugs = classItem.className?.flatMap { className ->
                                className.associatedDrug?.map { drug ->
                                    Medicine(
                                        name = drug.name,
                                        dose = drug.dose.orEmpty(),
                                        strength = drug.strength.orEmpty()
                                    )
                                } ?: emptyList()
                            } ?: emptyList()

                            val className2Drugs = classItem.className2?.flatMap { className ->
                                className.associatedDrug2?.map { drug ->
                                    Medicine(
                                        name = drug.name,
                                        dose = drug.dose.orEmpty(),
                                        strength = drug.strength.orEmpty()
                                    )
                                } ?: emptyList()
                            } ?: emptyList()

                            // Combine both className and className2 drugs
                            classNameDrugs + className2Drugs
                        } ?: emptyList()
                    } ?: emptyList()
                } ?: emptyList()
            }
            // Save to the database
            dao.insertAll(medicines)

            // Emit data from the database
            emitAll(dao.getAllMedicines())
        }
    }
}
