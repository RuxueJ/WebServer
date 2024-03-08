package webserver667.responses.writers;

import java.io.*;
import java.util.Map;

import webserver667.constant.Constants;
import webserver667.exceptions.responses.ServerErrorException;
import webserver667.requests.HttpRequest;

import webserver667.responses.HttpResponseCode;
import webserver667.responses.IResource;
import webserver667.utils.StringUtils;

public class ScriptResponseWriter extends ResponseWriter {

  public ScriptResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() throws ServerErrorException, IOException {
    InputStream inputStream = null;
    try {
      inputStream = executeScript();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      writeStatusLine(HttpResponseCode.OK);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        out.write((line + "\r\n").getBytes());
        out.flush();
      }
    }
    catch (Exception e) {
      throw new ServerErrorException(e);
    }
  }

  private InputStream executeScript() throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(resource.getPath().toString());
    // HTTP headers and protocol and querystring added into the environment
    Map<String, String> env = processBuilder.environment();
    request.getHeaders().forEach((k, v) -> env.put(Constants.ENV_PREFIX + k, v));
    String queryString = request.getQueryString();
    if (StringUtils.isNotEmpty(queryString)) {
      env.put(Constants.ENV_QUERY_STRING, queryString);
    }
    Process process = processBuilder.start();
    process.waitFor();
    return process.getInputStream();
  }
}
