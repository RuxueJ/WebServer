package webserver667.utils;

import org.junit.platform.commons.util.StringUtils;

/**
 * @author 7991uxug@gmail.com
 * @date 2/11/24 7:50 PM
 */
public class URIUtil {
    public static boolean isValidURI(String uri) {
        if (StringUtils.isBlank(uri)) return false;
        // TODO: other situation
        return true;
    }

    public static String getQueryStringFromURI(String uri) {
        if (!isValidURI(uri)) {
            return "";
        }
        int index = uri.indexOf("?");
        return index == -1 ? "" : uri.substring(index+1);
    }

    public static String removeQueryStringFromURI(String uri) {
        if (!isValidURI(uri)) {
            return "";
        }
        int index = uri.indexOf("?");
        return index == -1 ? uri : uri.substring(0, index);
    }
}
