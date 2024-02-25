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
    return Files.exists(URIUtil.getPasswordPath(getPath()));
  }

  @Override
  public boolean isScript() {
    Path toCheck = getPath().getParent();
    String pathStr = toCheck.toString();
    boolean containScript = false;

    for(String s : pathStr.split(System.getProperty("file.separator"))){
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
    String[] split = this.URI.split("\\.");
    String extension = split[split.length - 1];
    return this.mimeTypes != null
            && this.mimeTypes.getMimeTypeByExtension(extension) != null
            ? this.mimeTypes.getMimeTypeByExtension(extension)
            : null;
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
