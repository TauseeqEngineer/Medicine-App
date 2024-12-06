package com.android.medicineapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine_table")
data class Medicine(
    @PrimaryKey
    val name: String,
    val dose: String,
    val strength: String
)