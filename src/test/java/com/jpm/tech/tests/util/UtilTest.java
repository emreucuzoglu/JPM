package com.jpm.tech.tests.util;

import org.junit.Test;

import static com.jpm.tech.util.Util.dateOffsetToWeekday;
import static com.jpm.tech.util.Util.isSundayCurrency;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.LocalDate.of;
import static org.junit.Assert.*;


public class UtilTest {


    @Test
    public void isSundayCurrency_shouldReturnTrue() {
        assertTrue(isSundayCurrency("AED"));
        assertTrue(isSundayCurrency("SAR"));

    }

    @Test
    public void isSundayCurrency_shouldReturnFalse() {
        assertFalse(isSundayCurrency("EUR"));
        assertFalse(isSundayCurrency(""));
        assertFalse(isSundayCurrency(null));
    }

    @Test
    public void dateOffsetToWeekday_endOnFriday() {
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 15), FRIDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 16), FRIDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 17), FRIDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 18), FRIDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 19), FRIDAY));
        assertEquals(2, dateOffsetToWeekday(of(2017, 5, 20), FRIDAY));
        assertEquals(1, dateOffsetToWeekday(of(2017, 5, 21), FRIDAY));
    }

    @Test
    public void dateOffsetToWeekday_endOnThursday() {
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 14), THURSDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 15), THURSDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 16), THURSDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 17), THURSDAY));
        assertEquals(0, dateOffsetToWeekday(of(2017, 5, 18), THURSDAY));
        assertEquals(2, dateOffsetToWeekday(of(2017, 5, 19), THURSDAY));
        assertEquals(1, dateOffsetToWeekday(of(2017, 5, 20), THURSDAY));
    }

}