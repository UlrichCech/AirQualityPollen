package com.example.android.airqualitypollen.platform.persistence

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PersistentConverters {

    @TypeConverter
    fun fromStringToTimestamp(value: String?): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return if (value != null) {
            try {
                return sdf.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun fromTimestampToString(date: Date?): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return if (date != null) {
            try {
                return sdf.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            null
        } else {
            null
        }
    }
}
