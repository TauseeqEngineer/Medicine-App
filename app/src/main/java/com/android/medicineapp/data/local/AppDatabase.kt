package com.android.medicineapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.medicineapp.data.model.Medicine
import com.android.medicineapp.data.model.User

@Database(entities = [Medicine::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun userDao(): UserDao
}

