package ru.job4j.todo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String uri = req.getPathInfo().substring(1);
        Pattern pattern = Pattern.compile("^(((edit|delete|get)/([1-9]+[0-9]*))|add|getAll)$");
        Matcher matcher = pattern.matcher(uri);

        if (matcher.matches()) {
            String[] elements = uri.split("/");
            servletRequest.setAttribute("action", elements[0]);
            Integer id = (elements.length > 1) ? Integer.parseInt(elements[1]) : null;
            servletRequest.setAttribute("id", id);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
        }

    }
}
