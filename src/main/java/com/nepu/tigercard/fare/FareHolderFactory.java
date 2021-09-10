package com.nepu.tigercard.fare;


public class FareHolderFactory {
  public static FareHolder getFareHolder(String fromZone, String toZone) {
    var journeyType = fromZone + "-" + toZone;
    return switch (journeyType) {
      case "1-1" -> new Zone1To1FareHolder();
      case "2-2" -> new Zone2To2FareHolder();
      case "1-2", "2-1" -> new Zone1To2FareHolder();
      default -> throw new RuntimeException("No fares available for journey from Zone " + fromZone + " to Zone " + toZone);
    };
  }
}
