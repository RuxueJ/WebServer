package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.ResponseWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 11:57 AM
 */
public interface Processor {


    public  ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException;
}
