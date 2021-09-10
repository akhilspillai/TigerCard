package com.nepu.tigercard;

import java.time.LocalDateTime;

public record JourneyRecord(LocalDateTime date, String fromZone, String toZone) { }
