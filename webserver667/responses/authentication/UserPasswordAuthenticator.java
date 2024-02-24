package webserver667.responses.authentication;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.UnauthorizedResponseWriter;
import webserver667.utils.URIUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class UserPasswordAuthenticator extends UserAuthenticator {

  public UserPasswordAuthenticator(HttpRequest request, IResource resource) {
    super(request, resource);
  }

  @Override
  public boolean isAuthenticated() {
    String authorization = request.getHeader(Constants.AUTHORIZATION);
    Path passwordPath = URIUtil.getPasswordPath(resource.getPath());

    boolean userAuthorized = false;
    try {
      PasswordFileReader passwordFileReader = new PasswordFileReader(passwordPath);
      userAuthorized = passwordFileReader.isUserAuthorized(authorization.split(" ")[1]);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userAuthorized;
  }

  public int authenticateStatus() {
    String authorizationHeader = request.getHeader(Constants.AUTHORIZATION);
    if (resource.isProtected() && authorizationHeader == null) {
      return 401;
    }

    if (resource.isProtected() && authorizationHeader != null && !isAuthenticated()) {
      return 403;
    }

    return 0;
  }

}
