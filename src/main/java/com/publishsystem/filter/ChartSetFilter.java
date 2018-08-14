package com.publishsystem.filter;

import javax.servlet.*;
import java.io.IOException;

public class ChartSetFilter implements Filter {
    private String characterEncoding;
    private boolean enabled;

    @Override
    public void init(FilterConfig config) throws ServletException {
        characterEncoding = config.getInitParameter("characterEncoding");
        enabled = "true".equals(config.getInitParameter("enabled").trim());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (enabled || characterEncoding != null) {
            //设置编码方式
            request.setCharacterEncoding(characterEncoding);
        }
        //必须要有的-->执行后面的操作
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        characterEncoding = null;
    }
}
