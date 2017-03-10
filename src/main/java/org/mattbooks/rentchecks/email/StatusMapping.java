package org.mattbooks.rentchecks.email;

public class StatusMapping {
  public static String humanReadableStatus(String code) {
    if (code.equals("check.created")) {
      return "Rent check was created";
    } else if (code.equals("check.in_local_area")) {
      return "Rent check is in local delivery area";
    } else if (code.equals("check.in_transit")) {
      return "Rent check is in transit";
    } else if (code.equals("check.processed_for_delivery")) {
      return "Rent check has been processed for delivery";
    } else if (code.equals("check.rendered_pdf")) {
      return "Rent check PDF rendered";
    } else if (code.equals("check.rendered_thumbnails")) {
      return "Rent check thumbnails rendered";
    } else if (code.equals("check.re-routed")) {
      return "!!! Rent check is being re-routed!";
    } else if (code.equals("check.returned_to_sender")) {
      return "!!! Rent check is being returned!";
    } else {
      return code;
    }
  }
}