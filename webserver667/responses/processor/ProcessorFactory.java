package webserver667.responses.processor;

import webserver667.requests.HttpMethods;

/**
 * @author 7991uxug@gmail.com
 * @date 2/12/24 12:57 PM
 */
public class ProcessorFactory {
    public static Processor createProcessor(HttpMethods httpMethods) {
        switch (httpMethods) {
            case GET:
                return new GetProcessor();
            case HEAD:
                return new HeadProcessor();
            case PUT:
                return new PutProcessor();
            case POST:
                return new PostProcessor();
            case DELETE:
                return new DeleteProcessor();
            default:
                return null;
        }
    }
}
