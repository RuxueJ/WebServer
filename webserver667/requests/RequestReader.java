package webserver667.requests;

import webserver667.exceptions.responses.BadRequestException;
import webserver667.exceptions.responses.MethodNotAllowedException;
import webserver667.utils.StringUtils;
import webserver667.utils.URIUtil;

import java.io.IOException;
import java.io.InputStream;

public class RequestReader {
    private InputStream input;

    public RequestReader(InputStream input) {
        this.input = input;
    }

    public HttpRequest getRequest() throws BadRequestException, MethodNotAllowedException {
        HttpRequest httpRequest = new HttpRequest();
        // read method, identifier and version
        String line = readLine();
        validate(line);
        String[] methodIdentifierVersion = line.split(" ");

        httpRequest.setHttpMethod(HttpMethods.convertStringToHttpMethod(methodIdentifierVersion[0]));
        httpRequest.setUri(methodIdentifierVersion[1]);
        httpRequest.setVersion(methodIdentifierVersion[2]);

        httpRequest.setQueryString(URIUtil.getQueryStringFromURI(methodIdentifierVersion[1]));

        // read headers
        line = readLine();
        while (StringUtils.isNotEmpty(line)) {
            httpRequest.addHeader(line);
            line = readLine();
        }

        // read body
        int contentLength = httpRequest.getContentLength();
        try {
            httpRequest.setBody(input.readNBytes(contentLength));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpRequest;
    }

    private String readLine() {
        StringBuilder sb = new StringBuilder();
        try {
            int ch;
            while ((ch = input.read()) != -1) {
                if (ch == '\r') {
                    int nextChar = input.read();
                    if (nextChar == '\n') {
                        break;
                    } else {
                        sb.append((char) ch);
                        if (nextChar != -1) {
                            sb.append((char) nextChar);
                        }
                    }
                } else if (ch == '\n') {
                    break;
                } else {
                    sb.append((char) ch);
                }
            }
        } catch (IOException e) {
            // Log the exception or handle it as necessary
            e.printStackTrace();
            // Return null or a specific error message depending on your error handling strategy
            return "";
        }
        return sb.toString();
    }

    private void validate(String headLine) throws BadRequestException, MethodNotAllowedException {
        String[] methodIdentifierVersion = headLine.split(" ");
        if (methodIdentifierVersion.length != 3) {
            throw new BadRequestException();
        }
        if (HttpMethods.convertStringToHttpMethod(methodIdentifierVersion[0]) == null) {
            throw new MethodNotAllowedException();
        }
    }

}
