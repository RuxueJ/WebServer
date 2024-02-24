package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.ResponseWriter;
import webserver667.responses.writers.ScriptResponseWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 1:00 PM
 */
public class PostProcessor extends Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException {
        ResponseWriter writer = super.process(out, resource, request);
        if (writer != null) {
            return writer;
        }
        // TODO: execute script
        ResponseWriter responseWriter;
        if (resource.exists() && resource.isScript()) {
            responseWriter = new ScriptResponseWriter(out, resource, request);
        } else {
            responseWriter = new NotFoundResponseWriter(out, resource, request);
        }
        return responseWriter;
    }
}
