package com.yushun.blog.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static  String getBasePath(HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":9000" + path + "/";

        return basePath;
    }
}
