package webserver667.responses.writers;

import java.io.OutputStream;

import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.processor.Processor;
import webserver667.responses.processor.ProcessorFactory;

public class ResponseWriterFactory {
  public static ResponseWriter create(OutputStream out, IResource resource, HttpRequest request) {
    HttpMethods httpMethod = request.getHttpMethod();
    Processor processor = ProcessorFactory.createProcessor(httpMethod);
    return processor.process(out, resource, request);
  }
}
