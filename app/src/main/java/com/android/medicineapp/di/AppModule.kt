package com.android.medicineapp.di

import com.android.medicineapp.data.local.MedicineDao
import com.android.medicineapp.data.local.UserDao
import com.android.medicineapp.data.remote.api.ApiService
import com.android.medicineapp.data.repository.MedicineRepositoryImpl
import com.android.medicineapp.data.repository.UserRepositoryImpl
import com.android.medicineapp.domain.repository.MedicineRepository
import com.android.medicineapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provides ApiService instance for Retrofit API calls
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        // Retrofit builder to configure the API client
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())  // Converts JSON responses to Kotlin objects using Gson
            .build()  // Build the Retrofit client
            .create(ApiService::class.java)
    }

    // Provides MedicineRepository instance, which relies on ApiService and MedicineDao
    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        medicineDao: MedicineDao
    ): MedicineRepository {
        return MedicineRepositoryImpl(api = apiService, dao = medicineDao)
    }
}
