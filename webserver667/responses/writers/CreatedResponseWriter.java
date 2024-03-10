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

public class CreatedResponseWriter extends ResponseWriter {

  public CreatedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    byte[] body = Constants.BODY_CREATED.getBytes();
    // write other headers to response
    Map<String, String> otherHeaders = new HashMap<>();
    otherHeaders.put(Constants.HEADER_LOCATION, request.getUri());
    try {
      writePipeLine(
              HttpResponseCode.CREATED,
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
