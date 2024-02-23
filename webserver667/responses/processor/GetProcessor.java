package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 11:56 AM
 */
public class GetProcessor implements Processor{

    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException {
        ResponseWriter responseWriter;

        String ifModifiedSince = request.getHeader("If-Modified-Since");
        if(ifModifiedSince == null){
            if (resource.exists()) {
                responseWriter = new OkResponseWriter(out, resource, request);
            } else {
                responseWriter = new NotFoundResponseWriter(out, resource, request);
            }
        }else{
            if (resource.exists()) {

                String lastModified = String.valueOf(resource.lastModified());
                System.out.println("ifModifiedSince" + ifModifiedSince);
                System.out.println("lastModified" + lastModified);
                responseWriter = new OkResponseWriter(out, resource, request);
            } else {
                responseWriter = new NotFoundResponseWriter(out, resource, request);
            }



        }





        return responseWriter;
    }
}
