package com.publishsystem.filter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fzc
 * @date 2018-1-17 9:54
 */
public class LogFilter implements Filter {
    private Log log = LogFactory.getLog(LogFilter.class);
    private String filterName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName = filterConfig.getFilterName();
        log.info("启动 Filter:" + filterName);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        long startTime = System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        requestURI = request.getQueryString() == null ? requestURI : (requestURI + "?" + request.getQueryString());
        filterChain.doFilter(servletRequest, servletResponse);
        long endTime = System.currentTimeMillis();
        log.info(((HttpServletRequest) servletRequest).getRemoteAddr() + "访问了" + requestURI + ",总用时" + (endTime - startTime) + "毫秒");
    }

    @Override
    public void destroy() {
        log.info("关闭Filter：" + filterName);
    }
}
