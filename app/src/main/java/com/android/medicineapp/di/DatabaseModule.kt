package com.android.medicineapp.di

import android.content.Context
import androidx.room.Room
import com.android.medicineapp.data.local.AppDatabase
import com.android.medicineapp.data.local.MedicineDao
import com.android.medicineapp.data.local.UserDao
import com.android.medicineapp.data.repository.UserRepositoryImpl
import com.android.medicineapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "medicine_app_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMedicineDao(database: AppDatabase): MedicineDao {
        return database.medicineDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(dao = userDao)
    }
}
