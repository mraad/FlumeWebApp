package com.esri;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 */
@WebFilter(value = "/log/*")
public class LogFilter implements Filter
{
    private Logger m_logger = Logger.getLogger(getClass());

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException
    {
        if (servletRequest instanceof HttpServletRequest)
        {
            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            final String queryString = httpServletRequest.getQueryString();
            final String pathInfo = httpServletRequest.getPathInfo();
            if ("/debug".equals(pathInfo))
            {
                m_logger.debug(queryString);
            }
            else if ("/error".equals(pathInfo))
            {
                m_logger.error(queryString);
            }
            else if ("/warn".equals(pathInfo))
            {
                m_logger.warn(queryString);
            }
            else
            {
                m_logger.info(queryString);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {
    }
}
