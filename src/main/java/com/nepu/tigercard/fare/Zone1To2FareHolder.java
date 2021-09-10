package com.nepu.tigercard.fare;

class Zone1To2FareHolder implements FareHolder {

  @Override
  public int getFare(boolean isPeak) {
    if (isPeak) {
      return 35;
    }
    return 30;
  }

  @Override
  public int getDailyCappedFare() {
    return 120;
  }

  @Override
  public int getWeeklyCappedFare() {
    return 600;
  }
}
