package accounts.controller.interceptor;

import org.apache.catalina.connector.InputBuffer;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

    private ByteArrayOutputStream cachedBytes;

    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null) {
            cacheInputStream();
        }

        final ByteArrayInputStream input = new ByteArrayInputStream(cachedBytes.toByteArray());
        final InputBuffer inputBuffer = new InputBuffer();

        byte[] cachedBytesArray = cachedBytes.toByteArray();
        inputBuffer.read(cachedBytesArray, 0, cachedBytesArray.length);


        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return input.available() == 0;
            }

            @Override
            public boolean isReady() {
                return input.available() > 0;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                
            }

            @Override
            public int read() throws IOException {
                return input.read();
            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }
}
