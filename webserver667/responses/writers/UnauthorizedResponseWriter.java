package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import webserver667.constant.Constants;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class UnauthorizedResponseWriter extends ResponseWriter {

  public UnauthorizedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    Map<String, String> otherHeaders = new HashMap<>();
    otherHeaders.put(Constants.HEADER_WWW_AUTHENTICATE, Constants.HEADER_VALUE_WWW_AUTHENTICATE);
    byte[] body = Constants.BODY_UNAUTHORIZED.getBytes();
    try {
      writePipeLine(
              HttpResponseCode.UNAUTHORIZED,
              Constants.MIMETYPE_TEXT_PLAIN,
              body.length,
              body,
              otherHeaders
      );
    } catch (Exception e) {
      throw new ServerErrorException(e);
    }
  }

}
