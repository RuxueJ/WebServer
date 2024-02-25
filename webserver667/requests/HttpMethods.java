package webserver667.requests;

import java.util.HashSet;
import java.util.Set;

public enum HttpMethods {
  GET, HEAD, PUT, POST, DELETE;

  public static HttpMethods convertStringToHttpMethod(String method) {
    switch (method) {
      case "GET":
        return GET;
      case "HEAD":
        return HEAD;
      case "PUT":
        return PUT;
      case "POST":
        return POST;
      case "DELETE":
        return DELETE;
      default:
        return null;
    }
  }
}
