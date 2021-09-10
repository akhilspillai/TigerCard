package com.nepu.tigercard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FareCalculatorTest {
  private FareCalculator fareCalculator;
  @BeforeEach
  void setUp() {
    fareCalculator = new FareCalculator();
  }

  @Test
  @DisplayName("Should return zero for an empty list of journeys")
  void testEmptyJourneyList() {
    List<JourneyRecord> journeys = new ArrayList<>();
    assertEquals(0, fareCalculator.calculateFare(journeys), "fare must be 0");
  }

  @Test
  @DisplayName("Should throw exception for a journey with unimplemented zone")
  void testUnimplementedZoneException() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T12:11:30"), "1", "3"));
    Exception exception = assertThrows(RuntimeException.class, () -> fareCalculator.calculateFare(journeys));
    String expectedMessage = "No fares available for journey from Zone 1 to Zone 3";
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey in zone 1")
  void testSingleOffPeakJourneyZone1() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T12:11:30"), "1", "1"));
    assertEquals(25, fareCalculator.calculateFare(journeys), "fare must be 25");
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey in zone 2")
  void testSingleOffPeakJourneyZone2() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T12:11:30"), "2", "2"));
    assertEquals(20, fareCalculator.calculateFare(journeys), "fare must be 20");
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey from zone 1 to zone 2")
  void testSingleOffPeakJourneyFromZone1To2() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T12:11:30"), "1", "2"));
    assertEquals(30, fareCalculator.calculateFare(journeys), "fare must be 30");
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey from zone 2 to zone 1")
  void testSingleOffPeakJourneyFromZone2To1() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T12:11:30"), "2", "1"));
    assertEquals(30, fareCalculator.calculateFare(journeys), "fare must be 30");
  }

  @Test
  @DisplayName("Should return expected fare for peak hour single journey in zone 1")
  void testSinglePeakHourJourneyZone1() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:10"), "1", "1"));
    assertEquals(30, fareCalculator.calculateFare(journeys), "fare must be 30");
  }

  @Test
  @DisplayName("Should return expected fare for peak hour single journey in zone 2")
  void testSinglePeakHourJourneyZone2() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:10"), "2", "2"));
    assertEquals(25, fareCalculator.calculateFare(journeys), "fare must be 25");
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey from zone 1 to zone 2")
  void testSinglePeakHourJourneyFromZone1To2() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:10"), "1", "2"));
    assertEquals(35, fareCalculator.calculateFare(journeys), "fare must be 35");
  }

  @Test
  @DisplayName("Should return expected fare for off-peak hour single journey from zone 2 to zone 1")
  void testSinglePeakHourJourneyFromZone2To1() {
    List<JourneyRecord> journeys = List.of(new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:10"), "2", "1"));
    assertEquals(35, fareCalculator.calculateFare(journeys), "fare must be 35");
  }

  @Test
  @DisplayName("Should return expected fare for single day non-capped journeys in zone 1")
  void testNonCappedJourneysOnSingleDayInZone1() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-10T06:30:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-10T15:30:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-10T18:30:00"), "1", "1") // 30
    );
    assertEquals(80, fareCalculator.calculateFare(journeys), "fare must be 80");
  }

  @Test
  @DisplayName("Should return expected fare for single day non-capped journeys in zone 2")
  void testNonCappedJourneysOnSingleDayInZone2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-10T06:30:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-10T18:30:00"), "2", "2") // 25
    );
    assertEquals(70, fareCalculator.calculateFare(journeys), "fare must be 70");
  }

  @Test
  @DisplayName("Should return expected fare for single day non-capped journeys between zone 1 and 2")
  void testNonCappedJourneysOnSingleDayBetweenZone1And2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-10T06:30:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-10T07:30:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-10T15:30:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-10T18:30:00"), "2", "1") // 35
    );
    assertEquals(115, fareCalculator.calculateFare(journeys), "fare must be 115");
  }

  @Test
  @DisplayName("Should return expected fare for single week non-capped journeys in zone 1")
  void testNonCappedJourneysOnSingleWeekInZone1() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T22:45:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T12:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-18T09:10:00"), "1", "1") // 30
    );
    assertEquals(215, fareCalculator.calculateFare(journeys), "fare must be 215");
  }

  @Test
  @DisplayName("Should return expected fare for single week non-capped journeys in zone 1")
  void testNonCappedJourneysOnSingleWeekInZone2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T22:45:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T12:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-18T09:10:00"), "2", "2") // 25
    );
    assertEquals(175, fareCalculator.calculateFare(journeys), "fare must be 175");
  }

  @Test
  @DisplayName("Should return expected fare for single week non-capped journeys in zone 1")
  void testNonCappedJourneysOnSingleWeekBetweenZone1And2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T22:45:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T12:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-18T09:10:00"), "1", "2") // 35
    );
    assertEquals(225, fareCalculator.calculateFare(journeys), "fare must be 225");
  }

  @Test
  @DisplayName("Should return expected fare for single day capped journeys in zone 1")
  void testCappedJourneysOnSingleDayInZone1() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-11T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T19:10:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T22:45:00"), "1", "1") // 25
    );
    assertEquals(100, fareCalculator.calculateFare(journeys), "fare must be capped at 100");
  }

  @Test
  @DisplayName("Should return expected fare for single day capped journeys in zone 2")
  void testCappedJourneysOnSingleDayInZone2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-11T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T19:10:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-11T22:45:00"), "2", "2") // 20
    );
    assertEquals(80, fareCalculator.calculateFare(journeys), "fare must be capped at 80");
  }

  @Test
  @DisplayName("Should return expected fare for single day capped journeys between zone 1 and 2")
  void testCappedJourneysOnSingleDayBetweenZone1And2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-11T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-11T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-11T22:45:00"), "2", "2") // 20
    );
    assertEquals(120, fareCalculator.calculateFare(journeys), "fare must be capped at 120");
  }

  @Test
  @DisplayName("Should return expected fare for single week capped journeys in zone 1")
  void testCappedJourneysOnSingleWeekInZone1() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T19:10:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T19:10:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T19:10:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T19:10:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:15:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:40:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T19:10:00"), "1", "1")// 30
    );
    assertEquals(500, fareCalculator.calculateFare(journeys), "fare must be capped at 500");
  }

  @Test
  @DisplayName("Should return expected fare for single week capped journeys in zone 2")
  void testCappedJourneysOnSingleWeekInZone2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-13T19:10:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-14T19:10:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-15T19:10:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-16T19:10:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T06:50:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T10:20:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:15:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:40:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T18:40:00"), "2", "2"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T19:10:00"), "2", "2") // 25
    );
    assertEquals(400, fareCalculator.calculateFare(journeys), "fare must be capped at 400");
  }

  @Test
  @DisplayName("Should return expected fare for single week capped journeys between zone 1 and 2")
  void testCappedJourneysOnSingleWeekBetweenZone1And2() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-13T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-14T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-15T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-15T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-16T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-16T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-17T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-17T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-17T19:10:00"), "1", "2") // 35
    );
    assertEquals(600, fareCalculator.calculateFare(journeys), "fare must be capped at 600");
  }

  @Test
  @DisplayName("Should reset fare cap for journeys across days")
  void testCapResetForJourneysAcrossDays() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-11T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-11T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-11T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-11T22:45:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-12T09" 
            + ":45:00"), "1", "2") // 35
    );
    assertEquals(155, fareCalculator.calculateFare(journeys), "fare must be capped at 155");
  }

  @Test
  @DisplayName("Should reset fare cap for journeys across weeks")
  void testCapResetForJourneysAcrossWeeks() {
    List<JourneyRecord> journeys = List.of(
        new JourneyRecord(LocalDateTime.parse("2021-09-13T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-13T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-13T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-14T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-14T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-14T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-15T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-15T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-15T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-16T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T10:20:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-16T15:10:00"), "2", "2"),// 20
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:15:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T16:40:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T18:40:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-16T19:10:00"), "1", "2"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-17T06:50:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-17T10:20:00"), "1", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-18T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-18T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-18T15:10:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-19T15:10:00"), "1", "1"),// 25
        new JourneyRecord(LocalDateTime.parse("2021-09-19T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-19T15:10:00"), "2", "1"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-20T06:50:00"), "1", "2"),// 30
        new JourneyRecord(LocalDateTime.parse("2021-09-20T19:10:00"), "2", "1"),// 35
        new JourneyRecord(LocalDateTime.parse("2021-09-20T19:59:00"), "2", "1") // 35
    );
    assertEquals(700, fareCalculator.calculateFare(journeys), "fare must be 700");
  }
}
