package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.authentication.UserPasswordAuthenticator;
import webserver667.responses.writers.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 1:00 PM
 */
public class PutProcessor extends Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException {
        ResponseWriter writer = super.process(out, resource, request);
        if (writer != null) {
            return writer;
        }

        // create resource
        Path path = resource.getPath();
        if (path.getParent() != null && !Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.write(path, request.getBody());

        if (Files.exists(path)) {
            return new CreatedResponseWriter(out, resource, request);
        }
        return new InternalServerErrorResponseWriter(out, resource, request);
    }
}
