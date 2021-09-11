package com.nepu.tigercard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserRecordTest {
  private UserRecord userRecord;

  @BeforeEach
  public void setUp() {
    userRecord = new UserRecord();
  }

  @Test
  @DisplayName("Should initialize daily cap record")
  public void testDailyCapInit() {
    var journeyDate = LocalDateTime.parse("2021-09-11T10:22:40");
    var capAmount = 50;

    userRecord.updateDailyCapRecord(journeyDate, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-11T00:00"), userRecord.getDailyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getDailyCapRecord().getCapAmount(), "cap amount must be " + capAmount);
  }

  @Test
  @DisplayName("Should update daily cap record when a higher cap is passed")
  public void testDailyCapUpdate() {
    var journeyDate1 = LocalDateTime.parse("2021-09-11T10:22:40");
    userRecord.updateDailyCapRecord(journeyDate1, 30);

    var capAmount = 50;
    var journeyDate2 = LocalDateTime.parse("2021-09-11T19:45:00");
    userRecord.updateDailyCapRecord(journeyDate2, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-11T00:00"), userRecord.getDailyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getDailyCapRecord().getCapAmount(), capAmount, "cap amount must be " + capAmount);
  }

  @Test
  @DisplayName("Should reset daily cap when a new date is passed")
  public void testDailyCapReset() {
    var capAmount = 50;
    var journeyDate1 = LocalDateTime.parse("2021-09-11T10:22:40");
    userRecord.updateDailyCapRecord(journeyDate1, capAmount);
    userRecord.updateDailySpentAmount(20);

    var journeyDate2 = LocalDateTime.parse("2021-09-12T19:45:00");
    userRecord.updateDailyCapRecord(journeyDate2, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-12T00:00"), userRecord.getDailyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getDailyCapRecord().getCapAmount(), "cap amount must be " + capAmount);
    assertEquals(0, userRecord.getDailyCapRecord().getSpentAmount(), "spent amount must be 0");
  }

  @Test
  @DisplayName("Should initialize weekly cap record")
  public void testWeeklyCapInit() {
    var journeyDate = LocalDateTime.parse("2021-09-11T10:22:40");
    var capAmount = 50;

    userRecord.updateWeeklyCapRecord(journeyDate, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-06T00:00"), userRecord.getWeeklyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getWeeklyCapRecord().getCapAmount(), capAmount, "cap amount must be " + capAmount);
  }

  @Test
  @DisplayName("Should update weekly cap record when a higher cap is passed")
  public void testWeeklyCapUpdate() {
    var journeyDate1 = LocalDateTime.parse("2021-09-11T10:22:40");
    userRecord.updateWeeklyCapRecord(journeyDate1, 30);

    var capAmount = 50;
    var journeyDate2 = LocalDateTime.parse("2021-09-11T19:45:00");
    userRecord.updateWeeklyCapRecord(journeyDate2, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-06T00:00"), userRecord.getWeeklyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getWeeklyCapRecord().getCapAmount(), "cap amount must be " + capAmount);
  }

  @Test
  @DisplayName("Should reset weekly cap when a new date is passed")
  public void testWeeklyCapReset() {
    var capAmount = 50;
    var journeyDate1 = LocalDateTime.parse("2021-09-11T10:22:40");
    userRecord.updateWeeklyCapRecord(journeyDate1, capAmount);
    userRecord.updateDailySpentAmount(20);

    var journeyDate2 = LocalDateTime.parse("2021-09-14T19:45:00");
    userRecord.updateWeeklyCapRecord(journeyDate2, capAmount);

    assertEquals(LocalDateTime.parse("2021-09-13T00:00"), userRecord.getWeeklyCapRecord().getStartDate(), "start date must be synced");
    assertEquals(capAmount, userRecord.getWeeklyCapRecord().getCapAmount(), "cap amount must be " + capAmount);
    assertEquals(0, userRecord.getWeeklyCapRecord().getSpentAmount(), "spent amount must be 0");
  }

  @Test
  @DisplayName("Should update daily spent amount")
  public void testDailySpentUpdate() {
    assertEquals(0, userRecord.getDailyCapRecord().getSpentAmount(), "spent amount must be 0");

    userRecord.updateDailySpentAmount(20);
    assertEquals(20, userRecord.getDailyCapRecord().getSpentAmount(), "spent amount must be 20");

    userRecord.updateDailySpentAmount(30);
    assertEquals(50, userRecord.getDailyCapRecord().getSpentAmount(), "spent amount must be 50");
  }

  @Test
  @DisplayName("Should update weekly spent amount")
  public void testWeeklySpentUpdate() {
    assertEquals(0, userRecord.getWeeklyCapRecord().getSpentAmount(), "spent amount must be 0");

    userRecord.updateWeeklySpentAmount(20);
    assertEquals(20, userRecord.getWeeklyCapRecord().getSpentAmount(), "spent amount must be 20");

    userRecord.updateWeeklySpentAmount(30);
    assertEquals(50, userRecord.getWeeklyCapRecord().getSpentAmount(), "spent amount must be 50");
  }
}
