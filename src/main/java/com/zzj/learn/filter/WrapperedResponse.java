package com.zzj.learn.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class WrapperedResponse extends HttpServletResponseWrapper {
    private ByteArrayOutputStream buffer = null;
    private ServletOutputStream out = null;
    private PrintWriter writer = null;

    public WrapperedResponse(HttpServletResponse resp) throws IOException {
        super(resp);
        buffer = new ByteArrayOutputStream();//真正存储数据的流
        out = new WrapperedResponse.WapperedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
    }

    //重载父类获取outputstream的方法
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return out;
    }

    //重载父类获取writer的方法
    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {
        return writer;
    }

    //重载父类获取flushBuffer的方法
    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public String getContent() throws IOException {
        flushBuffer();//将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据
        return new String(buffer.toByteArray());
    }

    //内部类，对ServletOutputStream进行包装
    private class WapperedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos = null;

        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException {
            bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}
