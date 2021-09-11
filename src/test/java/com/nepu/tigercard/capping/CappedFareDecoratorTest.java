package com.nepu.tigercard.capping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nepu.tigercard.fare.FareHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CappedFareDecoratorTest {
  private CapRecord capRecord;
  private FareHolder fareHolder;
  private CappedFareDecorator cappedFareDecorator;

  @BeforeEach
  void setUp() {
    capRecord = new CapRecord();
    capRecord.setSpentAmount(20);
    capRecord.setCapAmount(100);
    fareHolder = mock(FareHolder.class);
    cappedFareDecorator = new CappedFareDecorator(fareHolder, capRecord);
  }

  @Test
  @DisplayName("Should return full fare when cap is not reached")
  void testReturnFulLFare() {
    when(fareHolder.getFare(false)).thenReturn(40);
    var fare = cappedFareDecorator.getFare(false);
    verify(fareHolder, times(1)).getFare(false);
    assertEquals(fare, 40, "fare must be 40");
  }

  @Test
  @DisplayName("Should return partial fare when cap is reached")
  void testReturnPartialFare() {
    when(fareHolder.getFare(false)).thenReturn(100);
    var fare = cappedFareDecorator.getFare(false);
    verify(fareHolder, times(1)).getFare(false);
    assertEquals(fare, 80, "fare must be 80");
  }

  @Test
  @DisplayName("Should return zero fare when cap is reached")
  void testReturnZeroFare() {
    capRecord.setSpentAmount(100);
    when(fareHolder.getFare(false)).thenReturn(40);
    var fare = cappedFareDecorator.getFare(false);
    verify(fareHolder, times(1)).getFare(false);
    assertEquals(fare, 0, "fare must be 0");
  }
}
