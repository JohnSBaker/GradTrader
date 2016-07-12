package com.scottlogic.gradtrader.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

public class DeviceTypeServletFilter implements Filter {
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = null;
        if (request instanceof HttpServletRequest) {
            final HttpServletRequest httpRequest = (HttpServletRequest) request;
            final String uri = httpRequest.getRequestURI();

            if (uri.equals("/")) {
                final String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
                if (userAgent == null || userAgent.toLowerCase().indexOf("mobi") == -1) {
                    requestDispatcher = request.getRequestDispatcher("/desktop.html");
                } else {
                    requestDispatcher = request.getRequestDispatcher("/mobile.html");
                }
            }
        }
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}
