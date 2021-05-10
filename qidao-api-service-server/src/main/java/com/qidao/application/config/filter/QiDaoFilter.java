package com.qidao.application.config.filter;

import com.qidao.application.config.wrapper.QidaoRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 * @author Autuan.Yu
 */
@Component
@WebFilter(urlPatterns = "/*")
public class QiDaoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        QidaoRequestWrapper requestWrapper = new QidaoRequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }
}
