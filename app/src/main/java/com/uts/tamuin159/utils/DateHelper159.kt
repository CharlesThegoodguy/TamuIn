package com.uts.tamuin166.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelper166 {

    private val localeId166 = Locale("id", "ID")

    fun formatTimestamp166(timestamp166: Long): String {
        val formatter166 = SimpleDateFormat("dd MMM yyyy, HH:mm", localeId166)
        return formatter166.format(Date(timestamp166))
    }

    fun formatCurrentDateHeader166(): String {
        val formatter166 = SimpleDateFormat("EEEE, dd MMM yyyy", localeId166)
        return formatter166.format(Date())
    }

    fun getStartOfDay166(): Long {
        val calendar166 = Calendar.getInstance()
        calendar166.set(Calendar.HOUR_OF_DAY, 0)
        calendar166.set(Calendar.MINUTE, 0)
        calendar166.set(Calendar.SECOND, 0)
        calendar166.set(Calendar.MILLISECOND, 0)
        return calendar166.timeInMillis
    }

    fun getEndOfDay166(): Long {
        val calendar166 = Calendar.getInstance()
        calendar166.set(Calendar.HOUR_OF_DAY, 23)
        calendar166.set(Calendar.MINUTE, 59)
        calendar166.set(Calendar.SECOND, 59)
        calendar166.set(Calendar.MILLISECOND, 999)
        return calendar166.timeInMillis
    }
}
