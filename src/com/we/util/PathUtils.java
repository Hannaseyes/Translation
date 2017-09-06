package com.we.util;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

public class PathUtils {
    /*
     * 获取项目的根目录 因为tomcat和weblogic获取的根目录不一致，所以需要此方法
     */
    public static String getWebRootUrl(HttpServletRequest request)
            throws MalformedURLException {
        String fileDirPath = request.getSession().getServletContext()
                .getRealPath("/");
        if (fileDirPath == null) {
            // 如果返回为空，则表示服务器为weblogic，则需要使用另外的方法
            return request.getSession().getServletContext().getResource("/")
                    .getFile();
        } else {
            return fileDirPath;
        }
    }
    
    public static String getBaseUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + contextPath + "/";
        return basePath;
    }
            
}
