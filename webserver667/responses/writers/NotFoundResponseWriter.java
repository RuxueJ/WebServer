package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.constant.Constants;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class NotFoundResponseWriter extends ResponseWriter {

  public NotFoundResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException{
    byte[] body = String.format(Constants.BODY_NOT_FOUND, resource.getPath()).getBytes();
    try {
      writePipeLine(
              HttpResponseCode.NOT_FOUND,
              Constants.MIMETYPE_TEXT_PLAIN,
              body.length,
              body,
              null
      );

  } catch (Exception e) {
    throw new ServerErrorException(e);
  }
}
}
