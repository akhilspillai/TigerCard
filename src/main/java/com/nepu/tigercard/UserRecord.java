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
    if (dailyCapRecord.getStartDate() == null || !dailyCapRecord.getStartDate().equals(startOfDay)) {
      dailyCapRecord.setStartDate(startOfDay);
      dailyCapRecord.setCapAmount(capAmount);
      dailyCapRecord.setSpentAmount(0);
      return;
    }
    dailyCapRecord.setCapAmount(Math.max(dailyCapRecord.getCapAmount(), capAmount));
  }

  public void updateWeeklyCapRecord(LocalDateTime journeyDate, int capAmount) {
    var startOfWeek = dateTimeUtil.resetToStartOfWeek(journeyDate);
    if (weeklyCapRecord.getStartDate() == null || !weeklyCapRecord.getStartDate().equals(startOfWeek)) {
      weeklyCapRecord.setStartDate(startOfWeek);
      weeklyCapRecord.setCapAmount(capAmount);
      weeklyCapRecord.setSpentAmount(0);
      return;
    }
    weeklyCapRecord.setCapAmount(Math.max(weeklyCapRecord.getCapAmount(), capAmount));
  }

  public void updateDailySpentAmount(int amount) {
    dailyCapRecord.setSpentAmount(dailyCapRecord.getSpentAmount() + amount);
  }

  public void updateWeeklySpentAmount(int amount) {
    weeklyCapRecord.setSpentAmount(weeklyCapRecord.getSpentAmount() + amount);
  }
}
