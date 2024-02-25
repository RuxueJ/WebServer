package webserver667.responses.writers;

import java.io.*;
import java.util.Map;

import org.junit.platform.commons.util.StringUtils;
import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;

public class ScriptResponseWriter extends ResponseWriter {

  public ScriptResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    InputStream inputStream = null;
    try {
      inputStream = executeScript();
    } catch (IOException | InterruptedException e) {
      try {
        writeStatusLine(HttpResponseCode.INTERNAL_SERVER_ERROR);
        return;
      } catch (IOException e2) {
      }
    }
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      writeStatusLine(HttpResponseCode.OK);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        out.write((line + "\r\n").getBytes());
        out.flush();
      }
    } catch (IOException e) {
    }
  }

  private InputStream executeScript() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(resource.getPath().toString());
    // HTTP headers and protocol and querystring added into the environment
    Map<String, String> env = processBuilder.environment();
    request.getHeaders().forEach((k, v) -> env.put(Constants.ENV_PREFIX + k, v));
    String queryString = request.getQueryString();
    if (StringUtils.isNotBlank(queryString)) {
      env.put(Constants.ENV_QUERY_STRING, queryString);
    }
    Process process = processBuilder.start();
    process.waitFor();
    return process.getInputStream();
  }
}
