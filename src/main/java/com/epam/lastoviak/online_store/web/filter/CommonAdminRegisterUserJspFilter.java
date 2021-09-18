package com.epam.lastoviak.online_store.web.filter;

import com.epam.lastoviak.online_store.web.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommonAdminRegisterUserJspFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (accessAllowed(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            String errorMessage = "You do not have permission to access the requested resource";

            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            httpRequest.getSession().setAttribute("errorMessage", errorMessage);
            //   log.trace("Set the request attribute: errorMessage --> " + errorMessage);

            servletRequest.getRequestDispatcher(Path.ERROR_PAGE)
                    .forward(httpRequest, servletResponse);
        }
    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        return session != null && session.getAttribute("account") != null;
    }
}
