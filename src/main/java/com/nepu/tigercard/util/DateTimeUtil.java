package com.nepu.tigercard.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {
  public boolean isPeakHour(LocalDateTime dateTime) {
    if (isWeekend(dateTime)) {
      return isTimeBetween(dateTime, 9, 0, 11, 0)
          || isTimeBetween(dateTime, 18, 0, 22, 0);
    }
    return isTimeBetween(dateTime, 7, 0, 10, 30)
        || isTimeBetween(dateTime, 17, 0, 20, 0);
  }

  private boolean isWeekend(LocalDateTime dateTime) {
    var dayOfWeek = dateTime.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }

  private boolean isTimeBetween(LocalDateTime dateTime, int startHour, int startMin, int endHour, int endMin) {
    int hour = dateTime.getHour();
    int min = dateTime.getMinute();
    return (hour > startHour || (hour == startHour && min >= startMin))
        && (hour < endHour || (hour == endHour && min <= endMin));
  }

  public LocalDateTime resetToStartOfWeek(LocalDateTime dateTime) {
    return resetToStartOfDay(dateTime).with(DayOfWeek.MONDAY);
  }

  public LocalDateTime resetToStartOfDay(LocalDateTime dateTime) {
    return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT);
  }
}
