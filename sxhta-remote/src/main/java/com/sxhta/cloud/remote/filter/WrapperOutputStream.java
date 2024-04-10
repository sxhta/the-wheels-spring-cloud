package com.sxhta.cloud.remote.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import okio.BufferedSink;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public final class WrapperOutputStream extends ServletOutputStream implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final BufferedSink bos;

    public WrapperOutputStream(BufferedSink bos) {
        this.bos = bos;
    }

    @Override
    public void write(int b) {
        try {
            bos.writeByte(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }
}