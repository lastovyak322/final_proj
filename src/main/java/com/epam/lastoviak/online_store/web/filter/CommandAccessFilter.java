package com.epam.lastoviak.online_store.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@WebFilter(urlPatterns = {})
public class CommandAccessFilter implements Filter {

        //private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

        // commands access
        private static Map<Integer, List<String>> accessMap = new HashMap<>();
        private static List<String> commons = new ArrayList<String>();
        private static List<String> outOfControl = new ArrayList<String>();

     

        @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

}
