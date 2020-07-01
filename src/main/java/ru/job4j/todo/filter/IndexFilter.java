package ru.job4j.todo.filter;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.HibStore;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The preprocess filter for index.jsp file
 * @author Roman Yakimkin (29.06.2020)
 * @since 29.06.2020
 * @version 1.0
 */
public class IndexFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        List<Item> items = HibStore.instOf().getItems();
        request.setAttribute("items", items);
        chain.doFilter(request, response);
    }
}
