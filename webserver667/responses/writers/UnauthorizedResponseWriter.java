package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class UnauthorizedResponseWriter extends ResponseWriter {

  public UnauthorizedResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {

    try {
      // Write the HTTP status line
      String statusLine = "HTTP/1.1 401 Unauthorized\r\n";
      out.write(statusLine.getBytes());

      String httpHeader = "WWW-Authenticate: Basic realm=\"667 Server\"\r\n";
      out.write(httpHeader.getBytes());

      // Write the Content-Length header
      String responseBody = "401 Unauthorized -This server could not verify that you are authorized to access the document requested.";
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
