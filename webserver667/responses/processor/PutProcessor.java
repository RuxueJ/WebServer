package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.ResponseWriter;

import java.io.OutputStream;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 1:00 PM
 */
public class PutProcessor implements Processor{
    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) {
        ResponseWriter responseWriter;
//        request.getUri()
        return null;
    }
}
