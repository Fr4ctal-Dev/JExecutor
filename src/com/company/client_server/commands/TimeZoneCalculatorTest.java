package com.company.client_server.commands;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Time;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class TimeZoneCalculatorTest {

    @Test
    void testGetCalendar() throws IOException {
        TimeZoneCalculator tzc = new TimeZoneCalculator();
        GregorianCalendar expectedCalendar = new GregorianCalendar();
        expectedCalendar.setTimeZone(TimeZone.getTimeZone("EST"));
        assertEquals(expectedCalendar, tzc.getCalendar("EST"));

    }
}