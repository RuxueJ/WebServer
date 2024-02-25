package webserver667.constant;

/**
 * @author 7991uxug@gmail.com
 * @date 2/23/24 8:32 PM
 */
public class Constants {
    // time patterns
    public static final String RFC7321_PATTERN = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    public static final String CLF_PATTERN = "dd/MMM/yyyy:HH:mm:ss Z";

    // HTTP version
    public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    // headers
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
    public static final String HEADER_VALUE_WWW_AUTHENTICATE = "Basic realm=\"667 Server\"";
    public static final String HEADER_ALLOW = "Allow";

    // scripts env variables
    public static final String ENV_QUERY_STRING = "QUERY_STRING";
    public static final String ENV_PREFIX = "HTTP_";

    // logger format
    public static final String LOGGER_STRING = "%s - - [%s] \"%s %s %s\" %d %d";

    // bodies
    public static final String BODY_BAD_REQUEST = "Bad Request: The request cannot be processed due to syntax error.";
    public static final String BODY_CREATED = "Resource successfully created.";
    public static final String BODY_FORBIDDEN = "You do not have permission to access this resource.";
    public static final String BODY_INTERNAL_SERVER_ERROR = "An unexpected error occurred.";
    public static final String BODY_METHOD_NOT_ALLOWED = "The requested method %s is not allowed for the URL %s.";
    public static final String BODY_NOT_FOUND = "The requested resource %s was not found.";
    public static final String BODY_UNAUTHORIZED = "Access is denied due to invalid credentials.";

    // mimetype
    public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
}
