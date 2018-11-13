package com.ckl.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2018/11/13 0013
 * creat_time: 15:01
 **/
public class BaseWebUtils {
    private static final int BUFFER_SIZE = 1024;

    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";


    public static String getParameters(HttpServletRequest request){
        InputStream is = null;
        try {
            is = new BufferedInputStream(request.getInputStream(), BUFFER_SIZE);
            int contentLength = Integer.valueOf(request.getHeader("Content-Length"));
            byte[] bytes = new byte[contentLength];
            int readCount = 0;
            while (readCount < contentLength) {
                readCount += is.read(bytes, readCount, contentLength - readCount);
            }
            return new String(bytes, "UTF-8");
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(is);
        }

        return null;
    }

    public static boolean isFormPost(HttpServletRequest request) {
        String contentType = request.getContentType();
        return (contentType != null && contentType.contains(FORM_CONTENT_TYPE) &&
                HttpMethod.POST.matches(request.getMethod()));
    }
}
