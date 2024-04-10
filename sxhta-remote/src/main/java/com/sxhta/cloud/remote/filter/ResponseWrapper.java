package com.sxhta.cloud.remote.filter;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import okio.Buffer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

/**
 * Response包装类
 */
public final class ResponseWrapper extends HttpServletResponseWrapper implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Buffer buffer;

    private final ServletOutputStream out;

    public ResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        buffer = new Buffer();
        out = new WrapperOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public void flushBuffer()
            throws IOException {
        if (out != null) {
            out.flush();
            buffer.flush();
        }
    }

    public byte[] getContent()
            throws IOException {
        flushBuffer();
        return buffer.readByteArray();
    }
}
