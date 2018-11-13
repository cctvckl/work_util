package com.ckl.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * desc:
 * https://stackoverflow.com/questions/10210645/http-servlet-request-lose-params-from-post-body-after-read-it-once/17129256#17129256
 * @author : caokunliang
 * creat_date: 2018/8/2 0002
 * creat_time: 13:46
 **/
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
        cachedBytes = new ByteArrayOutputStream();
        ServletInputStream inputStream = null;
        try {
            inputStream = super.getInputStream();
            IOUtils.copy(inputStream, cachedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        return new CachedServletInputStream(cachedBytes);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


}