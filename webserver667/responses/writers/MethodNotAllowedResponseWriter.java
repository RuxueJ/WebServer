package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class MethodNotAllowedResponseWriter extends ResponseWriter {

  public MethodNotAllowedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {

    try {
      // Write the HTTP status line
      String statusLine = "HTTP/1.1 405 Method Not Allowed\r\n";
      out.write(statusLine.getBytes());

      // Write the Content-Length header
      String responseBody = "405 Method Not Allowed: The requested method is not allowed. ";
      String contentLengthHeader = "Content-Length: " + responseBody.length() + "\r\n";
      out.write(contentLengthHeader.getBytes());

      // Write a blank line to separate headers from the body
      out.write("\r\n".getBytes());

      // Write the response body

      out.write(responseBody.getBytes());

      // Flush the output stream
      out.flush();
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }
  }

}
