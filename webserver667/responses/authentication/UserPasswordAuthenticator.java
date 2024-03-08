package webserver667.responses.authentication;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.utils.StringUtils;
import webserver667.utils.URIUtil;

import java.nio.file.Path;

public class UserPasswordAuthenticator extends UserAuthenticator {

  public UserPasswordAuthenticator(HttpRequest request, IResource resource) {
    super(request, resource);
  }

  @Override
  public boolean isAuthenticated() {
    String authorization = request.getHeader(Constants.HEADER_AUTHORIZATION);
    Path passwordPath = URIUtil.getPasswordPath(resource.getPath());

    boolean userAuthorized = false;
    try {
      PasswordFileReader passwordFileReader = new PasswordFileReader(passwordPath);
      userAuthorized = passwordFileReader.isUserAuthorized(authorization.split(" ")[1]);
    } catch (Exception e) {
    }
    return userAuthorized;
  }

  public int authenticateStatus() {
    String authorizationHeader = request.getHeader(Constants.HEADER_AUTHORIZATION);
    if (resource.isProtected() && StringUtils.isEmpty(authorizationHeader)) {
      return 401;
    }

    if (resource.isProtected() && StringUtils.isNotEmpty(authorizationHeader) && !isAuthenticated()) {
      return 403;
    }

    return 0;
  }

}
