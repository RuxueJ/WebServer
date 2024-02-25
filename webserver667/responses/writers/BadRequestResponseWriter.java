package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class BadRequestResponseWriter extends ResponseWriter {

  public BadRequestResponseWriter(OutputStream out, IResource resource, HttpRequest httpRequest) {
    super(out, resource, httpRequest);
  }

  @Override
  public void write() {
    String body = Constants.BODY_BAD_REQUEST;
    try {
      writePipeLine(
              HttpResponseCode.BAD_REQUEST,
              Constants.MIMETYPE_TEXT_PLAIN,
              body.length(),
              body,
              null
      );
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }
  }
}
