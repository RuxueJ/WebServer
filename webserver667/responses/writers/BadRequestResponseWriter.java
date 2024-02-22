package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;

public class BadRequestResponseWriter extends ResponseWriter {

  public BadRequestResponseWriter(OutputStream out, IResource resource, HttpRequest httpRequest) {
    super(out, resource, httpRequest);
  }

  @Override
  public void write() {

    try {
      // Write the HTTP status line
      String statusLine = "HTTP/1.1 400 Bad Request\r\n";
      out.write(statusLine.getBytes());

      // Write the Content-Length header
//      String responseBody = "400 Bad Request - The request could not be understood by the server due to malformed syntax.";
//      String contentLengthHeader = "Content-Length: " + responseBody.length() + "\r\n";
//      out.write(contentLengthHeader.getBytes());

      // Write a blank line to separate headers from the body
      out.write("\r\n".getBytes());

      // Write the response body

//      out.write(responseBody.getBytes());

      // Flush the output stream
      out.flush();
    } catch (IOException e) {
      // Handle IOException if necessary
      e.printStackTrace();
    }
  }
}
