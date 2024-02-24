package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;
import webserver667.utils.TimeStampUtil;

public class NoContentResponseWriter extends ResponseWriter {

  public NoContentResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    try {
      // Write the HTTP status line
      String statusLine = "HTTP/1.1 204 No Content\r\n";
      out.write(statusLine.getBytes());

      // write Last-Modified
      out.write(String.format("Last-Modified:%s\r\n", TimeStampUtil.convertFromTimestamp(resource.lastModified())).getBytes());
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
