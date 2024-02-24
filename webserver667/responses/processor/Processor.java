package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserPasswordAuthenticator;
import webserver667.responses.writers.ForbiddenResponseWriter;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.UnauthorizedResponseWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 11:57 AM
 */
public abstract class Processor {

    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException {
        UserPasswordAuthenticator authenticator = new UserPasswordAuthenticator(request, resource);
        int authenticateStatus = authenticator.authenticateStatus();
        if (authenticateStatus == 401) {
            return new UnauthorizedResponseWriter(out, resource, request);
        }
        if (authenticateStatus == 403) {
            return new ForbiddenResponseWriter(out, resource, request);
        }
        return null;
    }
}
