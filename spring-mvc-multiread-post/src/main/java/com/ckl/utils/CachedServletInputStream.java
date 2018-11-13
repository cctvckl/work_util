package com.ckl.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * An inputstream which reads the cached request body
 */
public class CachedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream input;

    public CachedServletInputStream(ByteArrayOutputStream cachedBytes) {
        // create a new input stream from the cached request body
        byte[] bytes = cachedBytes.toByteArray();
        input = new ByteArrayInputStream(bytes);
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}
