package com.example.tag_service.Util;

import java.util.Map;

public class HttpJob {
    /**
     * 生成get参数请求url
     * 示例：https://0.0.0.0:80/api?u=u&o=o
     * 示例：https://0.0.0.0:80/api
     *
     * @param protocol 请求协议 示例: http 或者 https
     * @param uri      请求的uri 示例: 0.0.0.0:80
     * @param data   请求参数
     * @return
     */
    static public String generateRequestParameters(String protocol, String uri, Map<String, Object> data) {
        StringBuilder sb = new StringBuilder(protocol).append("://").append(uri);
        if (data!=null) {
            sb.append("?");
            for (Map.Entry map : data.entrySet()) {
                sb.append(map.getKey())
                        .append("=")
                        .append(map.getValue())
                        .append("&");
            }
            uri = sb.substring(0, sb.length() - 1);
            return uri;
        }
        return sb.toString();
    }
}
