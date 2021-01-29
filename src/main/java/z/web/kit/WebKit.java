package z.web.kit;

import lombok.experimental.UtilityClass;

/**
 * Web kit
 *
 * @author biezhi
 * 2017/6/2
 */
@UtilityClass
public class WebKit {

    public static final String UNKNOWN_MAGIC = "unknown";
    // 所有路径
    public static final String ALL_PATH      = "+/*paths";
    /**
     * 从Request获取IP地址
     *
     * @param request request
     * @return IP地址
     */

    /**
     * Get the client IP address by request
     *
     * @param request Request instance
     * @return return ip address
     */
    /*public static String ipAddress(Request request) {
        String ipAddress = request.header("x-forwarded-for");
        if (StrKit.isBlank(ipAddress) || UNKNOWN_MAGIC.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.header("Proxy-Client-IP");
        }
        if (StrKit.isBlank(ipAddress) || UNKNOWN_MAGIC.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.header("WL-Proxy-Client-IP");
        }
        if (StrKit.isBlank(ipAddress) || UNKNOWN_MAGIC.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.header("X-Real-IP");
        }
        if (StrKit.isBlank(ipAddress) || UNKNOWN_MAGIC.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.header("HTTP_CLIENT_IP");
        }
        if (StrKit.isBlank(ipAddress) || UNKNOWN_MAGIC.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.header("HTTP_X_FORWARDED_FOR");
        }
        return ipAddress;
    }*/

}
