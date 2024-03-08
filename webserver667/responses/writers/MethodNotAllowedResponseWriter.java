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

import javax.swing.text.html.FormSubmitEvent;

public class MethodNotAllowedResponseWriter extends ResponseWriter {

  public MethodNotAllowedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException{
    Map<String, String> otherHeaders = new HashMap<>();
    otherHeaders.put(Constants.HEADER_ALLOW, FormSubmitEvent.MethodType.GET.toString());
    byte[] body = String.format(
            Constants.BODY_METHOD_NOT_ALLOWED,
            request.getHttpMethod().toString(),
            request.getUri()
    ).getBytes();
    try {
      writePipeLine(
              HttpResponseCode.METHOD_NOT_ALLOWED,
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
