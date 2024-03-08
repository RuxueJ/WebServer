package webserver667.logging;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.utils.TimestampUtil;

import java.io.IOException;

public class Logger {
  /**
   * Your implementation for logging to stdout should be placed here
   */
  public static String getLogString(
      String ipAddress,
      HttpRequest request,
      IResource resource,
      int statusCode,
      int bytesSent) {
      String method = null;
      String uri = null;
      String version = Constants.DEFAULT_HTTP_VERSION;
      if (request != null) {
          uri = request.getUri();
          version = request.getVersion();
          method = request.getHttpMethod().toString();
      }
    return String.format(
            Constants.LOGGER_STRING,
            ipAddress,
            TimestampUtil.getCurrentTimeInCLFPattern(),
            method,
            uri,
            version,
            statusCode,
            bytesSent
          );
  }
}
