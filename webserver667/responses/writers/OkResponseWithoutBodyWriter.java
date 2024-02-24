package webserver667.responses.writers;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

import java.io.IOException;
import java.io.OutputStream;

public class OkResponseWithoutBodyWriter extends ResponseWriter {

  public OkResponseWithoutBodyWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    long contentLength = 0;
    try {
      contentLength = resource.getFileSize();
    } catch (IOException e) {
    }
    String statusLine = "HTTP/1.1 200 OK\r\n";
    String contentTypeLine = "Content-Type: text/html\r\n";
    String contentLengthLine = String.format("Content-Length: %d\r\n", contentLength);
    try {
      out.write(statusLine.getBytes());
      out.write(contentTypeLine.getBytes());
      out.write(contentLengthLine.getBytes());
      out.write("\r\n".getBytes());
    } catch (IOException e) {
      System.out.println("write failed");
    }
  }

}
