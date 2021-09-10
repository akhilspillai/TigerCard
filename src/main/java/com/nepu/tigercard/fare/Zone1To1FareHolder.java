package com.nepu.tigercard.fare;

class Zone1To1FareHolder implements FareHolder {

  @Override
  public int getFare(boolean isPeak) {
    if (isPeak) {
      return 30;
    }
    return 25;
  }

  @Override
  public int getDailyCappedFare() {
    return 100;
  }

  @Override
  public int getWeeklyCappedFare() {
    return 500;
  }
}
