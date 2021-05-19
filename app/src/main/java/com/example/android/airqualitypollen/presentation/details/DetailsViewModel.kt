package com.example.android.airqualitypollen.presentation.details

import androidx.lifecycle.ViewModel
import java.util.*

class DetailsViewModel : ViewModel() {

    companion object {
        fun isLastUpdateOnSameDayOrYesterday(updatedAt: Date?): Boolean {
            if (updatedAt == null) return false
            val currentTime = GregorianCalendar()
            currentTime.time = Date()
            val lastUpdate = GregorianCalendar()
            lastUpdate.time = updatedAt
            return (currentTime.get(Calendar.YEAR) == lastUpdate.get(Calendar.YEAR)
                    && currentTime.get(Calendar.MONTH) == lastUpdate.get(Calendar.MONTH)
                    && currentTime.get(Calendar.DAY_OF_MONTH) == lastUpdate.get(Calendar.DAY_OF_MONTH))
                    || (currentTime.get(Calendar.YEAR) == lastUpdate.get(Calendar.YEAR)
                    && currentTime.get(Calendar.MONTH) == lastUpdate.get(Calendar.MONTH)
                    && (currentTime.get(Calendar.DAY_OF_MONTH) - 1) == lastUpdate.get(Calendar.DAY_OF_MONTH))
        }
    }

}