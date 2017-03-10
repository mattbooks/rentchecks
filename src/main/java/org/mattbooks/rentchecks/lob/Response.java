package org.mattbooks.rentchecks.lob;

public class Response {
  private final String message;

  public String getMessage() {
    return message;
  }

  public static Response success() {
    return new Response("SUCCESS");
  }

  private Response(String message) {
    this.message = message;
  }
}
