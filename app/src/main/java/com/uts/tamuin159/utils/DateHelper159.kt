package com.uts.tamuin159.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateHelper159 {

    private val localeId159 = Locale("id", "ID")

    fun formatTimestamp159(timestamp159: Long): String {
        val formatter159 = SimpleDateFormat("dd MMM yyyy, HH:mm", localeId159)
        return formatter159.format(Date(timestamp159))
    }

    fun formatCurrentDateHeader159(): String {
        val formatter159 = SimpleDateFormat("EEEE, dd MMM yyyy", localeId159)
        return formatter159.format(Date())
    }

    fun getStartOfDay159(): Long {
        val calendar159 = Calendar.getInstance()
        calendar159.set(Calendar.HOUR_OF_DAY, 0)
        calendar159.set(Calendar.MINUTE, 0)
        calendar159.set(Calendar.SECOND, 0)
        calendar159.set(Calendar.MILLISECOND, 0)
        return calendar159.timeInMillis
    }

    fun getEndOfDay159(): Long {
        val calendar159 = Calendar.getInstance()
        calendar159.set(Calendar.HOUR_OF_DAY, 23)
        calendar159.set(Calendar.MINUTE, 59)
        calendar159.set(Calendar.SECOND, 59)
        calendar159.set(Calendar.MILLISECOND, 999)
        return calendar159.timeInMillis
    }
}
