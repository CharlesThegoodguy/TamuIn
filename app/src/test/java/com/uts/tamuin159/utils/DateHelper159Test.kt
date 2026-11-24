package com.uts.tamuin159.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class DateHelper159Test {

    @Test
    fun testFormatTimestamp159() {
        val calendar159 = Calendar.getInstance()
        calendar159.set(2026, Calendar.NOVEMBER, 9, 14, 30, 0)
        val formatted159 = DateHelper159.formatTimestamp159(calendar159.timeInMillis)
        
        // Assert format contains date and time components
        assertTrue(formatted159.contains("09"))
        assertTrue(formatted159.contains("Nov"))
        assertTrue(formatted159.contains("2026"))
        assertTrue(formatted159.contains("14:30"))
    }

    @Test
    fun testDayBoundaries159() {
        val startOfDay159 = DateHelper159.getStartOfDay159()
        val endOfDay159 = DateHelper159.getEndOfDay159()

        assertTrue(startOfDay159 < endOfDay159)

        val calStart159 = Calendar.getInstance().apply { timeInMillis = startOfDay159 }
        assertEquals(0, calStart159.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, calStart159.get(Calendar.MINUTE))
        assertEquals(0, calStart159.get(Calendar.SECOND))
        assertEquals(0, calStart159.get(Calendar.MILLISECOND))

        val calEnd159 = Calendar.getInstance().apply { timeInMillis = endOfDay159 }
        assertEquals(23, calEnd159.get(Calendar.HOUR_OF_DAY))
        assertEquals(59, calEnd159.get(Calendar.MINUTE))
        assertEquals(59, calEnd159.get(Calendar.SECOND))
        assertEquals(999, calEnd159.get(Calendar.MILLISECOND))
    }
}
