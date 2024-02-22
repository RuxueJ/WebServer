package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.NoContentResponseWriter;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.ResponseWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 1:00 PM
 */
public class DeleteProcessor implements Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) {
        ResponseWriter responseWriter;

        // delete resource
        Path path = resource.getPath();
        if (resource.exists()) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.println("Failed to delete resource");
            }
            if (resource.exists()) {
                responseWriter = new NotFoundResponseWriter(out, resource, request);
            } else {
                responseWriter = new NoContentResponseWriter(out, resource, request);
            }
        } else {
            responseWriter = new NotFoundResponseWriter(out, resource, request);
        }
        return responseWriter;
    }
}
