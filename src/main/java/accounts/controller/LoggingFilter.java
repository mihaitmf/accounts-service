package accounts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Component
public class LoggingFilter extends DelegatingFilterProxy {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        log.info("Started LoggingFilter...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final byte[] requestCopy = FileCopyUtils.copyToByteArray(request.getInputStream());

        log.info("HTTP REQUEST: {} {} - contents: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), new String(requestCopy));

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ByteArrayInputStreamWrapper(requestCopy);
            }
        };

        filterChain.doFilter(requestWrapper, response);
    }

    private class ByteArrayInputStreamWrapper extends ServletInputStream {

        private byte[] contents;
        private volatile int offset = 0;

        public ByteArrayInputStreamWrapper(byte[] contents) {
            this.contents = contents;
        }

        @Override
        public boolean isFinished() {
            return offset == contents.length;
        }

        @Override
        public boolean isReady() {
            return !isFinished();
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            if (offset > contents.length - 1) {
                return -1;
            }
            return contents[offset++];
        }
    }
}
