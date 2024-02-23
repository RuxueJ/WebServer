package webserver667.responses.writers;

import java.io.*;
import java.nio.file.Path;

import webserver667.requests.HttpRequest;

import webserver667.responses.IResource;

public class ScriptResponseWriter extends ResponseWriter {

  public ScriptResponseWriter(OutputStream out, IResource resource, HttpRequest request) {
    super(out, resource, request);
  }

  @Override
  public void write() {
    String statusLine = "HTTP/1.1 200 OK\r\n";
    try (InputStream inputStream = executeScript(resource.getPath())){
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      out.write(statusLine.getBytes());
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        out.write((line + "\r\n").getBytes());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private InputStream executeScript(Path scriptPath) throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(scriptPath.toString());
    Process process = processBuilder.start();
    process.waitFor();
    return process.getInputStream();
  }
}
