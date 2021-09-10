package com.nepu.tigercard.fare;

public interface FareHolder {
  int getFare(boolean isPeak);
  int getDailyCappedFare();
  int getWeeklyCappedFare();
}
