package com.android.medicineapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MedicineDTO(
    val problems: List<Problem>
)

data class Problem(
    val Diabetes: List<Diabetes>? = null,
    val Asthma: List<Asthma>? = null
)

data class Diabetes(
    val medications: List<Medication>? = null
)

data class Medication(
    val medicationsClasses: List<MedicationClass>? = null
)

data class MedicationClass(
    val className: List<ClassName>? = null,
    val className2: List<ClassName>? = null
)

data class ClassName(
    val associatedDrug: List<Drug>? = null,
    @SerializedName("associatedDrug#2")
    val associatedDrug2: List<Drug>? = null
)

data class Drug(
    val name: String,
    val dose: String?,
    val strength: String?
)

data class Asthma(
    val someField: String? = null // Example placeholder, modify as needed
)




