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
    return String.format(
            Constants.LOGGER_STRING,
            ipAddress,
            TimestampUtil.getCurrentTimeInCLFPattern(),
            request.getHttpMethod().toString(),
            request.getUri(),
            request.getVersion(),
            statusCode,
            bytesSent
          );
  }
}
