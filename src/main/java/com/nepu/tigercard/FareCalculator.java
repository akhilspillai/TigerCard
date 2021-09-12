package com.nepu.tigercard;

import com.nepu.tigercard.capping.CappedFareDecorator;
import com.nepu.tigercard.capping.FareDecorator;
import com.nepu.tigercard.fare.FareHolderFactory;
import com.nepu.tigercard.util.DateTimeUtil;

import java.util.List;

public class FareCalculator {
  private final DateTimeUtil dateTimeUtil;
  private final UserRecord userRecord;

  public FareCalculator() {
    dateTimeUtil = new DateTimeUtil();
    userRecord = new UserRecord();
  }

  public int calculateFare(List<JourneyRecord> journeys) {
    var totalFare = 0;
    for (var journey : journeys) {
      totalFare += calculateFare(journey);
    }
    return totalFare;
  }

  private int calculateFare(JourneyRecord journey) {
    var fareHolder = FareHolderFactory.getFareHolder(journey.fromZone(), journey.toZone());

    // update capping details of the user based on the current journey
    userRecord.updateDailyCapRecord(journey.date(), fareHolder.getDailyCappedFare());
    userRecord.updateWeeklyCapRecord(journey.date(), fareHolder.getWeeklyCappedFare());

    // apply daily capping
    FareDecorator fareDecorator = new CappedFareDecorator(fareHolder, userRecord.getDailyCapRecord());
    // apply weekly capping
    fareDecorator = new CappedFareDecorator(fareDecorator, userRecord.getWeeklyCapRecord());

    var isPeak = dateTimeUtil.isPeakHour(journey.date());
    var fare = fareDecorator.getFare(isPeak);

    userRecord.updateDailySpentAmount(fare);
    userRecord.updateWeeklySpentAmount(fare);

    return fare;
  }
}
