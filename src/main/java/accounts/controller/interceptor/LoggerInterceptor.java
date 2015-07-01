package accounts.controller.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private String fetchRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = request.getInputStream();

        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            while (reader.read(charBuffer) > 0) {
                stringBuilder.append(charBuffer);
            }
        } else {
            stringBuilder.append("");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("################## preHandle #########################");
        System.out.println(request.getRequestURI());

        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);

        String body = fetchRequestBody(multiReadRequest);
        String clientIP = multiReadRequest.getRemoteHost();
        int clientPort = request.getRemotePort();
        String uri = multiReadRequest.getRequestURI();

//        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(request);
//
//        String body = myRequestWrapper.getBody();
//        String clientIP = myRequestWrapper.getRemoteHost();
//        int clientPort = request.getRemotePort();
//        String uri = myRequestWrapper.getRequestURI();


        System.out.println(body);
        System.out.println(clientIP);
        System.out.println(clientPort);
        System.out.println(uri);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
