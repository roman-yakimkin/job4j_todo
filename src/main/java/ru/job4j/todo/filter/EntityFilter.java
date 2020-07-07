package ru.job4j.todo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityFilter implements Filter {
    private Map<String, String> patterns = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patterns.put("item.do", "^((([1-9]+[0-9]*)/(edit|delete|view|get))|add|getAll)$");
        patterns.put("user.do", "^((([1-9]+[0-9]*)/(edit|delete|view|get))|login|logout|register)$");
    }

    @Override
    public void destroy() {

    }

    private String getPatternText(String uri) {
        String result = null;
        for (Map.Entry<String, String> entry : patterns.entrySet()) {
            if (uri.contains("/" + entry.getKey() + "/")) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String pathInfo = req.getPathInfo();
        String uri = req.getRequestURI();
        boolean isPathParsed = false;
        System.out.println(req.getPathInfo() + " - " + req.getRequestURI());
        if (pathInfo != null) {
            pathInfo = pathInfo.substring(1);
            String patternText = getPatternText(uri);
            if (patternText != null) {
                Pattern pattern = Pattern.compile(patternText);
                Matcher matcher = pattern.matcher(pathInfo);
                if (matcher.matches()) {
                    isPathParsed = true;
                    String[] elements = pathInfo.split("/");
                    String action = (elements.length > 1) ? elements[1] : elements[0];
                    Integer id = (elements.length > 1) ? Integer.parseInt(elements[0]) : null;
                    servletRequest.setAttribute("action", action);
                    servletRequest.setAttribute("id", id);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
        if (!isPathParsed) {
            servletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
        }
    }
}