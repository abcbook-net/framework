package net.abcbook.framework.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author summer
 * @date 2017/8/15 上午11:29
 * 日志工具类
 */
public class RequestUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * @param request HttpServletRequest
     * @return String clientIp
     * 获取请求客户端的ip
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!UNKNOWN.equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    /**
     * 获取用户的请求方式, 用于判断是否为 ajax 请求
     * @param request
     * @return String requestedWith
     * 获取用户的请求类型
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }
}
