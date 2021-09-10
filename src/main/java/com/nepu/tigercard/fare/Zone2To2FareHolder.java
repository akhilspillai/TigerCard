package com.nepu.tigercard.fare;

class Zone2To2FareHolder implements FareHolder {

  @Override
  public int getFare(boolean isPeak) {
    if (isPeak) {
      return 25;
    }
    return 20;
  }

  @Override
  public int getDailyCappedFare() {
    return 80;
  }

  @Override
  public int getWeeklyCappedFare() {
    return 400;
  }
}
