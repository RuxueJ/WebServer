package webserver667.responses.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

import webserver667.requests.HttpMethods;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.processor.Processor;
import webserver667.responses.processor.ProcessorFactory;

public class ResponseWriterFactory {
  public static ResponseWriter create(OutputStream out, IResource resource, HttpRequest request) throws IOException{
    HttpMethods httpMethod = request.getHttpMethod();
    if (httpMethod != null) {
      Processor processor = ProcessorFactory.createProcessor(httpMethod);
      return processor.process(out, resource, request);
    }

    return new BadRequestResponseWriter(out, resource, request);
  }

}
