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

    // Provides a singleton instance of the Room database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "medicine_app_db"
        ).fallbackToDestructiveMigration() // Automatically migrates the database on schema changes
            .build()
    }

    // Provides an instance of the MedicineDao for database interactions related to medicine
    @Provides
    fun provideMedicineDao(database: AppDatabase): MedicineDao {
        return database.medicineDao()
    }

    // Provides an instance of the UserDao for database interactions related to user data
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    // Provides a singleton instance of UserRepository, which handles user-related data operations
    @Provides
    @Singleton
    fun provideRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(dao = userDao)
    }
}

