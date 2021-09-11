package com.nepu.tigercard;

import com.nepu.tigercard.capping.CapDecorator;
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

    userRecord.updateDailyCapRecord(journey.date(), fareHolder.getDailyCappedFare());
    userRecord.updateWeeklyCapRecord(journey.date(), fareHolder.getWeeklyCappedFare());

    FareDecorator fareDecorator = new CapDecorator(fareHolder, userRecord.getDailyCapRecord());
    fareDecorator = new CapDecorator(fareDecorator, userRecord.getWeeklyCapRecord());

    var isPeak = dateTimeUtil.isPeakHour(journey.date());
    var fare = fareDecorator.getFare(isPeak);

    userRecord.updateDailySpentAmount(fare);
    userRecord.updateWeeklySpentAmount(fare);

    return fare;
  }
}
