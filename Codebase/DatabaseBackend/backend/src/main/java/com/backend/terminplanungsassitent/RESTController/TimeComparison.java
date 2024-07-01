package com.backend.terminplanungsassitent.RESTController;

import java.time.Duration;
import java.time.LocalTime;

// Class to call a static method which compares two LocalTime objects and determines if they are within 2 hours of each other.
public class TimeComparison {

    /**
     * Checks if two LocalTime objects are within 2 hours of each other.
     * 
     * @param time1
     * @param time2
     * @return true if within 2h
     */
    public static boolean areTimesWithinTwoHours(LocalTime time1, LocalTime time2) {
        // Calculate the duration between the two times
        Duration duration = Duration.between(time1, time2);

        // Get the absolute value of the duration in hours
        long hours = Math.abs(duration.toHours());

        // Check if the duration is within 2 hours
        return hours <= 2;
    }
}
