package com.epam.lastoviak.online_store.web.filter;

import com.epam.lastoviak.online_store.web.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


public class CommandAccessFilter implements Filter {

    //private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

    // commands access
    private static Map<Integer, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        commons = asList(filterConfig.getInitParameter("commonForRegisterUserAndAdmin"));
        accessMap.put(1,commons);
        accessMap.put(2, asList(filterConfig.getInitParameter("admin")));
    }

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

        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }

        Integer roleId = (Integer) session.getAttribute("roleId");

        if (roleId == null) {
            return false;
        }
        return accessMap.get(roleId).contains(commandName)
                || commons.contains(commandName);
    }


    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

}
