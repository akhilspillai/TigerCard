package com.nepu.tigercard.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
  public boolean isPeakHour(LocalDateTime dateTime) {
    if (isWeekend(dateTime)) {
      return isTimeBetween(dateTime.toLocalTime(), LocalTime.parse("09:00"), LocalTime.parse("11:00"))
          || isTimeBetween(dateTime.toLocalTime(), LocalTime.parse("18:00"), LocalTime.parse("22:00"));
    }
    return isTimeBetween(dateTime.toLocalTime(), LocalTime.parse("07:00"), LocalTime.parse("10:30"))
        || isTimeBetween(dateTime.toLocalTime(), LocalTime.parse("17:00"), LocalTime.parse("20:00"));
  }

  private boolean isWeekend(LocalDateTime dateTime) {
    var dayOfWeek = dateTime.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }

  private boolean isTimeBetween(LocalTime dateTime, LocalTime startTime, LocalTime endTime) {
    return dateTime.isAfter(startTime) && dateTime.isBefore(endTime);
  }

  public LocalDateTime resetToStartOfWeek(LocalDateTime dateTime) {
    return resetToStartOfDay(dateTime).with(DayOfWeek.MONDAY);
  }

  public LocalDateTime resetToStartOfDay(LocalDateTime dateTime) {
    return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT);
  }
}
