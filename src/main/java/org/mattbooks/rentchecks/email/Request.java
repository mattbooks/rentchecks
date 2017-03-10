package org.mattbooks.rentchecks.email;

public class Request {
  private String eventType;

  public Request() {}

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return eventType;
  }
}
