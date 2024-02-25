package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;
import webserver667.utils.TimestampUtil;

public class NoContentResponseWriter extends ResponseWriter {

  public NoContentResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    try {
      writePipeLine(
              HttpResponseCode.NO_CONTENT,
              null,
              0,
              null,
              null
      );
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }
  }

  @Override
  protected void writeCommonHeaders(String mimeType, long contentLength) throws IOException {
    Map<String, String> commonHeaders = new HashMap<>();
    commonHeaders.put(Constants.HEADER_DATE, TimestampUtil.getCurrentTimeRFC7321());
    super.writeHeaders(commonHeaders);
  }

}
