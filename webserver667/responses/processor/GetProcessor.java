package webserver667.responses.processor;

import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.NotFoundResponseWriter;
import webserver667.responses.writers.NotModifiedResponseWriter;
import webserver667.responses.writers.OkResponseWriter;
import webserver667.responses.writers.ResponseWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 11:56 AM
 */
public class GetProcessor implements Processor{

    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException{
        ResponseWriter responseWriter;

        String ifModifiedSince = request.getHeader("If-Modified-Since").replace(" GMT","");
        long lastModified = resource.lastModified();


        if(ifModifiedSince == null){
            if (resource.exists()) {
                responseWriter = new OkResponseWriter(out, resource, request);
            } else {
                responseWriter = new NotFoundResponseWriter(out, resource, request);
            }
        }else{
            if (resource.exists()) {
                long timestamp = 100;
                try{  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(ifModifiedSince, formatter);
                    timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                } catch (Exception e){
                    e.printStackTrace();

                }

                System.out.println("ifModifiedSince" + timestamp);
                System.out.println("lastModified" + lastModified);

                if(timestamp >= lastModified){
                    responseWriter = new NotModifiedResponseWriter(out, resource, request);
                }else{
                    responseWriter = new OkResponseWriter(out, resource, request);
                }


            } else {
                responseWriter = new NotFoundResponseWriter(out, resource, request);
            }



        }





        return responseWriter;
    }
}
