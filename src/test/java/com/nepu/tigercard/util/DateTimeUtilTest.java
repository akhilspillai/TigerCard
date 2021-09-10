package com.nepu.tigercard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateTimeUtilTest {
  private DateTimeUtil dateTimeUtil;

  @BeforeEach
  void setUp() {
    dateTimeUtil = new DateTimeUtil();
  }

  @Test
  @DisplayName("Should return false for off-peak hours in weekdays")
  void testIsPeakHourInOffPeakWeekdays() {
    var dateTime = LocalDateTime.parse("2021-09-10T12:12:54");
    assertFalse(dateTimeUtil.isPeakHour(dateTime));
  }

  @Test
  @DisplayName("Should return true for peak hours in weekdays")
  void testIsPeakHourInPeakWeekdays() {
    var dateTime = LocalDateTime.parse("2021-09-10T07:12:54");
    assertTrue(dateTimeUtil.isPeakHour(dateTime));
  }

  @Test
  @DisplayName("Should return true for peak hours in weekends")
  void testIsPeakHourInOffPeakWeekends() {
    var dateTime = LocalDateTime.parse("2021-09-11T07:12:54");
    assertFalse(dateTimeUtil.isPeakHour(dateTime));
  }

  @Test
  @DisplayName("Should return true for peak hours in weekends")
  void testIsPeakHourInPeakWeekends() {
    var dateTime = LocalDateTime.parse("2021-09-11T19:12:54");
    assertTrue(dateTimeUtil.isPeakHour(dateTime));
  }

  @Test
  @DisplayName("Should reset a date to start of day")
  void testDateResetToStartOfDay() {
    var dateTime = LocalDateTime.parse("2021-09-11T19:12:54");
    var startOfDay = LocalDateTime.parse("2021-09-11T00:00:00");
    assertEquals(dateTimeUtil.resetToStartOfDay(dateTime), startOfDay);
  }

  @Test
  @DisplayName("Should reset a date to start of week")
  void testDateResetToStartOfWeek() {
    var dateTime = LocalDateTime.parse("2021-09-11T19:12:54");
    var startOfDay = LocalDateTime.parse("2021-09-06T00:00:00");
    assertEquals(dateTimeUtil.resetToStartOfWeek(dateTime), startOfDay);
  }
}
