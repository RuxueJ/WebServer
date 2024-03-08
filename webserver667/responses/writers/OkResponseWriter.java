package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class OkResponseWriter extends ResponseWriter {

  public OkResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    try {
     long contentLength = resource.getFileSize();
    byte[] body = request.getHttpMethod() != HttpMethods.HEAD ? resource.getFileBytes() : null;
    writePipeLine(
            HttpResponseCode.OK,
            resource.getMimeType(),
            contentLength,
            body,
            null
    );
    } catch (Exception e) {
      throw new ServerErrorException(e);
    }
  }

}
