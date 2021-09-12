package com.nepu.tigercard.capping;

import com.nepu.tigercard.fare.FareHolder;

/**
 * A decorator class to apply fare capping based on the amount already spent and the cap amount.
 */
public class CappedFareDecorator extends FareDecorator {
  private final CapRecord capRecord;

  public CappedFareDecorator(FareHolder fareHolder, CapRecord capRecord) {
    super(fareHolder);
    this.capRecord = capRecord;
  }

  @Override
  public int getFare(boolean isPeak) {
    var fare = this.fareHolder.getFare(isPeak);
    return this.applyFareCap(fare);
  }

  private int applyFareCap(int fare) {
    if (capRecord.getSpentAmount() + fare > capRecord.getCapAmount()) {
      return capRecord.getCapAmount() - capRecord.getSpentAmount();
    }
    return fare;
  }
}