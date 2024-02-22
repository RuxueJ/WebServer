package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class ForbiddenResponseWriter extends ResponseWriter {

  public ForbiddenResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {

    try {
      // Write the HTTP status line
      String statusLine = "HTTP/1.1 403 Forbidden\r\n";
      out.write(statusLine.getBytes());

      // Write a blank line to separate headers from the body
      out.write("\r\n".getBytes());

      // Flush the output stream
      out.flush();
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }
  }

}
