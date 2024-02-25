package webserver667.requests;

import startup.configuration.MimeTypes;
import webserver667.constant.Constants;
import webserver667.utils.URIUtil;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String uri;
    private HttpMethods method;
    private String queryString;
    private String version;
    private final Map<String, String> header;
    private MimeTypes mimeType;

    private byte[] body;


    public HttpRequest() {
        this.uri = "";
        this.method = HttpMethods.GET;
        this.queryString = "";
        this.version = Constants.DEFAULT_HTTP_VERSION;
        this.header = new HashMap<>();
        this.body = new byte[0];
        this.mimeType = null;

    }

    public HttpRequest(String uri, HttpMethods method, String queryString, String version, Map<String, String> header, byte[] body) {
        this.uri = uri;
        this.method = method;
        this.queryString = queryString;
        this.version = version;
        this.header = header;
        this.body = body;
    }

    public HttpMethods getHttpMethod() {

        return this.method;
    }

    public void setHttpMethod(HttpMethods method) {
        this.method = method;

    }

    public String getUri() {
        return URIUtil.removeQueryStringFromURI(this.uri);
    }

    public void setUri(String uri) {
        this.uri = uri;

    }

    public String getQueryString() {
        return URIUtil.getQueryStringFromURI(this.uri);
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;

    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;

    }

    public String getHeader(String expectedHeaderName) {
        String headerContent = this.header.getOrDefault(expectedHeaderName, null);
        return headerContent == null ? null : headerContent.strip();
    }

    public void addHeader(String headerLine) {
        String key = headerLine.split(":")[0];
        String value = headerLine.replace(key+":", "");
        this.header.put(key, value);

    }

    public Map<String, String> getHeaders() {
        return this.header;
    }

    public int getContentLength() {
        return Integer.parseInt(this.header.getOrDefault("Content-Length", "0").strip());
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean hasBody() {
        return this.body.length > 0;
    }


    public MimeTypes getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeTypes mimeType) {
        this.mimeType = mimeType;
    }
}
