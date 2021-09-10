package com.nepu.tigercard.capping;

import com.nepu.tigercard.UserRecord;
import com.nepu.tigercard.fare.FareHolder;
import com.nepu.tigercard.util.DateTimeUtil;

import java.time.LocalDateTime;

public class CappedFareDecorator extends FareDecorator {
  private final DateTimeUtil dateTimeUtil;
  private final UserRecord userRecord;
  private final LocalDateTime journeyDate;

  public CappedFareDecorator(FareHolder fareHolder, LocalDateTime journeyDate, UserRecord userRecord) {
    super(fareHolder);
    dateTimeUtil = new DateTimeUtil();
    this.userRecord = userRecord;
    this.journeyDate = journeyDate;
  }

  @Override
  public int getFare(boolean isPeak) {
    var fare = this.fareHolder.getFare(isPeak);

    this.updateDailyCapRecord(journeyDate, this.fareHolder.getDailyCappedFare());
    this.updateWeeklyCapRecord(journeyDate, this.fareHolder.getWeeklyCappedFare());

    fare = this.applyFareCap(fare, userRecord.getDailyCapRecord());
    return this.applyFareCap(fare, userRecord.getWeeklyCapRecord());
  }

  private void updateDailyCapRecord(LocalDateTime journeyDate, int capAmount) {
    var startOfDay = dateTimeUtil.resetToStartOfDay(journeyDate);
    if (userRecord.getDailyCapRecord().getStartDate() == null || !userRecord.getDailyCapRecord().getStartDate().equals(startOfDay)) {
      userRecord.getDailyCapRecord().setStartDate(startOfDay);
      userRecord.getDailyCapRecord().setCapAmount(capAmount);
      userRecord.getDailyCapRecord().setSpentAmount(0);
      return;
    }
    userRecord.getDailyCapRecord().setCapAmount(Math.max(userRecord.getDailyCapRecord().getCapAmount(), capAmount));
  }

  private void updateWeeklyCapRecord(LocalDateTime journeyDate, int capAmount) {
    var startOfWeek = dateTimeUtil.resetToStartOfWeek(journeyDate);
    if (userRecord.getWeeklyCapRecord().getStartDate() == null || !userRecord.getWeeklyCapRecord().getStartDate().equals(startOfWeek)) {
      userRecord.getWeeklyCapRecord().setStartDate(startOfWeek);
      userRecord.getWeeklyCapRecord().setCapAmount(capAmount);
      userRecord.getWeeklyCapRecord().setSpentAmount(0);
      return;
    }
    userRecord.getWeeklyCapRecord().setCapAmount(Math.max(userRecord.getWeeklyCapRecord().getCapAmount(), capAmount));
  }

  private int applyFareCap(int fare, CapRecord capRecord) {
    if (capRecord.getSpentAmount() + fare > capRecord.getCapAmount()) {
      var spent = capRecord.getSpentAmount();
      capRecord.setSpentAmount(capRecord.getCapAmount());
      return capRecord.getCapAmount() - spent;
    }
    capRecord.setSpentAmount(capRecord.getSpentAmount() + fare);
    return fare;
  }
}