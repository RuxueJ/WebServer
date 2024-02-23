package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.CreatedResponseWriter;
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
public class PutProcessor implements Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) {
        ResponseWriter responseWriter;

        // create resource
        Path path = resource.getPath();
        try {
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Failed to create resource");
        }

        if (resource.exists()) {
            responseWriter = new CreatedResponseWriter(out, resource, request);
        } else {
//            responseWriter = new NotFoundResponseWriter(out, resource, request);
            responseWriter = new CreatedResponseWriter(out, resource, request);
        }
        return responseWriter;
    }
}
