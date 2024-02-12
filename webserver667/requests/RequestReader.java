package webserver667.requests;

import java.io.InputStream;

public class RequestReader {
  private InputStream input;


  public RequestReader(InputStream input) {
    this.input = input;
  }

  public HttpRequest getRequest() {
    HttpRequest httpRequest = new HttpRequest();
    this.input.





    return httpRequest;
  }

}
