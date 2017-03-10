package org.mattbooks.rentchecks.email;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public class Response {
  private final int statusCode;
  private final Map<String, String> headers;
  private final String body;

  private Response(int statusCode, Map<String, String> headers, String body) {
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public static Response success() {
    return new Response(200, ImmutableMap.<String, String>of(), "{}");
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }
}
