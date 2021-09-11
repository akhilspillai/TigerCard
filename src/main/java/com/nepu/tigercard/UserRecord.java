package com.nepu.tigercard;

import com.nepu.tigercard.capping.CapRecord;
import com.nepu.tigercard.util.DateTimeUtil;

import java.time.LocalDateTime;

public class UserRecord {
  private final DateTimeUtil dateTimeUtil;
  private final CapRecord dailyCapRecord;
  private final CapRecord weeklyCapRecord;

  public UserRecord() {
    dateTimeUtil = new DateTimeUtil();
    dailyCapRecord = new CapRecord();
    weeklyCapRecord = new CapRecord();
  }

  public CapRecord getDailyCapRecord() {
    return dailyCapRecord;
  }

  public CapRecord getWeeklyCapRecord() {
    return weeklyCapRecord;
  }
  
  public void updateDailyCapRecord(LocalDateTime journeyDate, int capAmount) {
    var startOfDay = dateTimeUtil.resetToStartOfDay(journeyDate);
    updateCapRecord(dailyCapRecord, startOfDay, capAmount);
  }

  public void updateWeeklyCapRecord(LocalDateTime journeyDate, int capAmount) {
    var startOfWeek = dateTimeUtil.resetToStartOfWeek(journeyDate);
    updateCapRecord(weeklyCapRecord, startOfWeek, capAmount);
  }

  private void updateCapRecord(CapRecord capRecord, LocalDateTime startDate, int capAmount) {
    if (capRecord.getStartDate() == null || !capRecord.getStartDate().equals(startDate)) {
      capRecord.setStartDate(startDate);
      capRecord.setCapAmount(capAmount);
      capRecord.setSpentAmount(0);
      return;
    }
    capRecord.setCapAmount(Math.max(capRecord.getCapAmount(), capAmount));
  }

  public void updateDailySpentAmount(int amount) {
    dailyCapRecord.setSpentAmount(dailyCapRecord.getSpentAmount() + amount);
  }

  public void updateWeeklySpentAmount(int amount) {
    weeklyCapRecord.setSpentAmount(weeklyCapRecord.getSpentAmount() + amount);
  }
}
