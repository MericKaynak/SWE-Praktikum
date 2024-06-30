package com.backend.terminplanungsassitent.RESTController;

import java.time.Duration;
import java.time.LocalTime;

public class TimeComparison {

    public static boolean areTimesWithinTwoHours(LocalTime time1, LocalTime time2) {
        // Calculate the duration between the two times
        Duration duration = Duration.between(time1, time2);

        // Get the absolute value of the duration in hours
        long hours = Math.abs(duration.toHours());

        // Check if the duration is within 2 hours
        return hours <= 2;
    }
}
