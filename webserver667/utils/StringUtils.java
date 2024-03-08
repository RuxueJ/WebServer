package webserver667.utils;

/**
 * @author 7991uxug@gmail.com
 * @date 2/28/24 1:40 PM
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }
}
