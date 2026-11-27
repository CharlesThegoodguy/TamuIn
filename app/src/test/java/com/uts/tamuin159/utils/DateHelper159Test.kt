package com.uts.tamuin166.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class DateHelper166Test {

    @Test
    fun testFormatTimestamp166() {
        val calendar166 = Calendar.getInstance()
        calendar166.set(2026, Calendar.NOVEMBER, 9, 14, 30, 0)
        val formatted166 = DateHelper166.formatTimestamp166(calendar166.timeInMillis)
        
        // Assert format contains date and time components
        assertTrue(formatted166.contains("09"))
        assertTrue(formatted166.contains("Nov"))
        assertTrue(formatted166.contains("2026"))
        assertTrue(formatted166.contains("14:30"))
    }

    @Test
    fun testDayBoundaries166() {
        val startOfDay166 = DateHelper166.getStartOfDay166()
        val endOfDay166 = DateHelper166.getEndOfDay166()

        assertTrue(startOfDay166 < endOfDay166)

        val calStart166 = Calendar.getInstance().apply { timeInMillis = startOfDay166 }
        assertEquals(0, calStart166.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, calStart166.get(Calendar.MINUTE))
        assertEquals(0, calStart166.get(Calendar.SECOND))
        assertEquals(0, calStart166.get(Calendar.MILLISECOND))

        val calEnd166 = Calendar.getInstance().apply { timeInMillis = endOfDay166 }
        assertEquals(23, calEnd166.get(Calendar.HOUR_OF_DAY))
        assertEquals(59, calEnd166.get(Calendar.MINUTE))
        assertEquals(59, calEnd166.get(Calendar.SECOND))
        assertEquals(999, calEnd166.get(Calendar.MILLISECOND))
    }
}
