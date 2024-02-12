package webserver667.requests;

import org.junit.platform.commons.util.StringUtils;
import webserver667.exceptions.responses.BadRequestException;
import webserver667.exceptions.responses.MethodNotAllowedException;
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
        while (StringUtils.isNotBlank(line)) {
            httpRequest.addHeader(line);
            line = readLine();
        }

        // read body
        try {
            httpRequest.setBody(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpRequest;
    }

    private String readLine() {
        int byteRead;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((byteRead = this.input.read()) != 10) {
                stringBuilder.append((char) byteRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().strip();
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
