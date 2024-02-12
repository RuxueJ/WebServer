package webserver667.responses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import startup.configuration.MimeTypes;
import webserver667.requests.HttpRequest;
import webserver667.responses.authentication.UserAuthenticator;
import webserver667.responses.authentication.UserPasswordAuthenticator;
import webserver667.utils.URIUtil;

public class Resource implements IResource {

  private String URI;
  private String queryString;
  private String documentRoot;
  private MimeTypes mimeTypes;


  public Resource() {
    super();
  }
  public Resource(String uri, String queryString, String documentRoot, MimeTypes mimeTypes) {
      this.URI = URIUtil.removeQueryStringFromURI(uri);
      this.queryString = queryString;
      this.documentRoot = documentRoot;
      this.mimeTypes = mimeTypes;
  }

  @Override
  public boolean exists() {
    return Files.exists(getPath());
  }

  @Override
  public Path getPath() {
    return Paths.get(this.documentRoot, this.URI);
  }

  @Override
  public boolean isProtected() {
    Path parent = getPath().getParent();
    return Files.exists(Paths.get(String.valueOf(parent), ".passwords"));
  }

  @Override
  public boolean isScript() {
    Path toCheck = getPath().getParent();
    String pathStr = toCheck.toString();
    boolean containScript = false;

    for(String s : pathStr.split("-|\\\\")){
      if ("scripts".equals(s)) {
        containScript = true;
        break;
      }
    }
    return containScript;
  }

  @Override
  public UserAuthenticator getUserAuthenticator(HttpRequest request) {
    return new UserPasswordAuthenticator(request, this);
  }

  @Override
  public String getMimeType() {
    // TODO: need fix
    return mimeTypes.toString();
  }

  @Override
  public long getFileSize() throws IOException {
    return Files.size(getPath());
  }

  @Override
  public byte[] getFileBytes() throws IOException {
    return Files.readAllBytes(getPath());
  }

  @Override
  public long lastModified() throws IOException {
    FileTime lastModifiedTime = Files.getLastModifiedTime(getPath());
    return lastModifiedTime.toMillis();
  }

}
