package com.nepu.tigercard.fare;

/**
 * An interface to be implemented by classes which hold data related to fare between zones.
 */
public interface FareHolder {
  int getFare(boolean isPeak);
  int getDailyCappedFare();
  int getWeeklyCappedFare();
}
