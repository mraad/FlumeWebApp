package com.esri;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
@WebServlet(urlPatterns = "/log/*", loadOnStartup = 1)
public class LogServlet extends HttpServlet
{
    @Override
    protected void doGet(
            final HttpServletRequest req,
            final HttpServletResponse res) throws ServletException, IOException
    {
        /**
         * Do some logic here to handle web requests
         */
        req.setAttribute("qs", req.getQueryString());
        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }
}
