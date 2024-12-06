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

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        medicineDao: MedicineDao
    ): MedicineRepository {
        return MedicineRepositoryImpl(api = apiService, dao = medicineDao)
    }


}
