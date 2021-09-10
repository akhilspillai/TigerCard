package com.nepu.tigercard.capping;

import com.nepu.tigercard.fare.FareHolder;

public abstract class FareDecorator implements FareHolder {
  protected final FareHolder fareHolder;

  public FareDecorator(FareHolder fareHolder) {
    this.fareHolder = fareHolder;
  }

  @Override
  public int getFare(boolean isPeak) {
    return fareHolder.getFare(isPeak);
  }

  @Override
  public int getDailyCappedFare() {
    return fareHolder.getDailyCappedFare();
  }

  @Override
  public int getWeeklyCappedFare() {
    return fareHolder.getWeeklyCappedFare();
  }
}
