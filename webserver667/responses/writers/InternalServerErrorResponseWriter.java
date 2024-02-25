package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class InternalServerErrorResponseWriter extends ResponseWriter {

  public InternalServerErrorResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    String body = Constants.BODY_INTERNAL_SERVER_ERROR;
    try {
      writePipeLine(
              HttpResponseCode.INTERNAL_SERVER_ERROR,
              Constants.MIMETYPE_TEXT_PLAIN,
              body.length(),
              body,
              null
      );
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }}

}
