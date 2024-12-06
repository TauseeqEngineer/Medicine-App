package com.android.medicineapp.data.remote.api

import com.android.medicineapp.data.remote.dto.MedicineDTO
import retrofit2.http.GET

interface ApiService {
    @GET("v3/a75733bd-a223-464e-9e00-a2ada56aacac")
    suspend fun getMedicines(): MedicineDTO
}
