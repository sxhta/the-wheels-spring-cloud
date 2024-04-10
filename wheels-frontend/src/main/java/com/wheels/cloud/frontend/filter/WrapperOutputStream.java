package com.wheels.cloud.frontend.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayOutputStream;
import java.io.Serial;
import java.io.Serializable;

public final class WrapperOutputStream extends ServletOutputStream implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ByteArrayOutputStream bos;

    public WrapperOutputStream(ByteArrayOutputStream bos) {
        this.bos = bos;
    }

    @Override
    public void write(int b) {
        bos.write(b);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }
}