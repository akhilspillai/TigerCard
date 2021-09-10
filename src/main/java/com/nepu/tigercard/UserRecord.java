package com.nepu.tigercard;

import com.nepu.tigercard.capping.CapRecord;

public class UserRecord {
  private final CapRecord dailyCapRecord;
  private final CapRecord weeklyCapRecord;

  public UserRecord() {
    this.dailyCapRecord = new CapRecord();
    this.weeklyCapRecord = new CapRecord();
  }

  public CapRecord getDailyCapRecord() {
    return dailyCapRecord;
  }

  public CapRecord getWeeklyCapRecord() {
    return weeklyCapRecord;
  }
}
