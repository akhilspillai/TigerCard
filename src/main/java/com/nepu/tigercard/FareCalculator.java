package com.nepu.tigercard;

import com.nepu.tigercard.capping.CappedFareDecorator;
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
    var isPeak = dateTimeUtil.isPeakHour(journey.date());
    var fareHolder = FareHolderFactory.getFareHolder(journey.fromZone(), journey.toZone());
    var fareDecorator = new CappedFareDecorator(fareHolder, journey.date(), userRecord);
    return fareDecorator.getFare(isPeak);
  }
}
