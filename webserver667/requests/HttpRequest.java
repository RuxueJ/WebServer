package webserver667.requests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
  private String uri;
  private HttpMethods method;
  private String queryString;
  private String version;
  private Map<String,String> header;
  private byte[] body;



  public HttpMethods getHttpMethod() {

    return this.method;
  }

  public void setHttpMethod(HttpMethods method) {
    this.method = method;

  }

  public String getUri() {

    if (this.uri != null){
      int index = this.uri.indexOf("?");
      if (index != -1){
        return uri.substring(0,index);
      }else{
        return this.uri;
      }
    }else{
      return null;
    }
  }

  public void setUri(String uri) {
    this.uri = uri;


  }

  public String getQueryString() {
    if (this.uri == null){
      return null;
    }
    int index = this.uri.indexOf("?");
    if (index == -1){
      return null;
    }else{
      return this.uri.substring(index+1);
    }

  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;

  }

  public String getVersion() {

    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;

  }

  public String getHeader(String expectedHeaderName) {

    return this.header.get(expectedHeaderName).strip();
  }

  public void addHeader(String headerLine) {
    if (this.header == null){
      this.header = new HashMap<>();
    }
    String key = headerLine.split(":")[0];
    String value = headerLine.split(":")[1];
    this.header.put(key,value);

  }

  public int getContentLength() {
    if (this.header == null){
      return 0;
    }


    return Integer.parseInt(this.header.getOrDefault("Content-Length","0").strip());
  }

  public byte[] getBody() {
    return hasBody() ? this.body : new byte[0];
  }

  public void setBody(byte[] body) {
    this.body = body;

  }

  public boolean hasBody() {

    return this.body != null && this.body.length > 0;
  }


}
