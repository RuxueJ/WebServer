package webserver667.responses.processor;

import webserver667.constant.Constants;
import webserver667.requests.HttpRequest;
import webserver667.responses.IResource;
import webserver667.responses.writers.*;
import webserver667.utils.TimestampUtil;

import java.io.IOException;
import java.io.OutputStream;


/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 11:56 AM
 */
public class GetProcessor extends Processor{

    @Override
    public ResponseWriter process(OutputStream out, IResource resource, HttpRequest request) throws IOException{
        ResponseWriter writer = super.process(out, resource, request);
        if (writer != null) {
            return writer;
        }

        // When the resource does not exist, return NotFoundResponseWriter immediately
        if (!resource.exists()) {
            return new NotFoundResponseWriter(out, resource, request);
        }

        String ifModifiedSince = request.getHeader(Constants.HEADER_IF_MODIFIED_SINCE);
        long lastModified = resource.lastModified();

        // If the If-Modified-Since header is missing, or the resource has been updated (timestamp < lastModified), return OkResponseWriter
        if (ifModifiedSince == null) {
            return new OkResponseWriter(out, resource, request);
        }

        // Convert the If-Modified-Since header to a timestamp
        long timestamp = TimestampUtil.convertToTimestamp(ifModifiedSince);

        // If the resource has not been modified, return NotModifiedResponseWriter
        if (timestamp >= lastModified) {
            return new NotModifiedResponseWriter(out, resource, request);
        }

        // In all other cases, return OkResponseWriter
        return new OkResponseWriter(out, resource, request);
    }
}
