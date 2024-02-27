package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.platform.commons.util.CollectionUtils;
import org.junit.platform.commons.util.StringUtils;
import webserver667.constant.Constants;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;
import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;
import webserver667.utils.TimestampUtil;

public abstract class ResponseWriter {
  protected IResource resource;
  protected OutputStream out;
  protected HttpRequest request;
  private HttpResponseCode responseCode;

  public ResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    this.out = out;
    this.resource = resource;
    this.request = request;
  }

  public abstract void write() throws ServerErrorException, IOException;

  public int getResponseCode() {
    return responseCode.getCode();
  }

  public void writePipeLine(
          HttpResponseCode httpResponseCode,
          String mimeType,
          long contentLength,
          String body,
          Map<String, String> otherHeaders
    ) throws IOException {
    // [OPTIONAL] 0. preparation if required
    doAction();
    // 1. write statue line
    writeStatusLine(httpResponseCode);
    // 2. write common headers
    writeCommonHeaders(mimeType, contentLength);
    // 3. write other headers
    writeHeaders(otherHeaders);
    // 4. write empty line
    writeEmptyLine();
    // 5. write body
    writeBody(body);
    // 6. flush
    out.flush();
  }

  protected void doAction() {
    // default no-op
  }

  protected void writeStatusLine(HttpResponseCode httpresponseCode) throws IOException {
    this.responseCode = httpresponseCode;
    String statusLine = String.format("%s %d %s\r\n", request.getVersion(), responseCode.getCode(), responseCode.getReasonPhrase());
    out.write(statusLine.getBytes());
  }

   protected void writeCommonHeaders(String mimeType, long contentLength) throws IOException {
    Map<String, String> commonHeaders = new HashMap<>();
    commonHeaders.put(Constants.HEADER_CONTENT_TYPE, mimeType);
    commonHeaders.put(Constants.HEADER_CONTENT_LENGTH, String.valueOf(contentLength));
    commonHeaders.put(Constants.HEADER_DATE, TimestampUtil.getCurrentTimeRFC7321());
    writeHeaders(commonHeaders);
  }

  protected void writeBody(String body) throws IOException{
    if (StringUtils.isNotBlank(body)) {
      out.write(body.getBytes());
    }
  }

  protected void writeEmptyLine() throws IOException {
    out.write("\r\n".getBytes());
  }

  protected void writeHeaders(Map<String, String> headers) throws IOException {
    if (headers == null || headers.size() == 0) {
      return;
    }
    for (Map.Entry<String, String> entry: headers.entrySet()) {
      out.write(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()).getBytes());
    }
  }
}
