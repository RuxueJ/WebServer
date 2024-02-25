package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserPasswordAuthenticator;
import webserver667.responses.writers.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 1:00 PM
 */
public class DeleteProcessor extends Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException {
        ResponseWriter writer = super.process(out, resource, request);
        if (writer != null) {
            return writer;
        }
        // delete resource
        Path path = resource.getPath();
        if (!Files.exists(path)) {
            return new NotFoundResponseWriter(out, resource, request);
        }
        Files.delete(path);
        if (!Files.exists(path)) {
            return new NoContentResponseWriter(out, resource, request);
        }

        return new InternalServerErrorResponseWriter(out, resource, request);
    }
}
