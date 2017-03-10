package org.mattbooks.rentchecks.helpers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {
  public static String nextMonth() {
    ZonedDateTime now = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("America/Los_Angeles"));

    return String.format("%tB", now.plusMonths(1));
  }
}
