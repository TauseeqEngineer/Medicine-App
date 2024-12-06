package com.android.medicineapp.utils

import android.util.Log
import java.util.Calendar

// Function to get a greeting message based on the current time of day
fun getGreetingMessage(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good Morning"
        hour < 18 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

// Function to check if it's currently day time (6 AM to 6 PM)
fun isDayTime(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return hour in 6..18 // Daytime from 6 AM to 6 PM
}

// Function to record log messages
fun recordLog(message: String)
{
        Log.d("XYZ", message)
}
