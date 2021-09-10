package com.nepu.tigercard.capping;

import java.time.LocalDateTime;

public class CapRecord {
  private LocalDateTime startDate;
  private int capAmount;
  private int spentAmount;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public int getCapAmount() {
    return capAmount;
  }

  public void setCapAmount(int capAmount) {
    this.capAmount = capAmount;
  }

  public int getSpentAmount() {
    return spentAmount;
  }

  public void setSpentAmount(int spentAmount) {
    this.spentAmount = spentAmount;
  }
}
