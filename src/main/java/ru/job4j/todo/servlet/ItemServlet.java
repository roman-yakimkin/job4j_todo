package ru.job4j.todo.servlet;

import org.json.simple.JSONObject;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.store.HibStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Item Servlet class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.06.2020
 * @version 1.0
 */
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("real path = " + req.getServletContext().getRealPath(""));
        String action = (String) req.getAttribute("action");
        Integer id = (Integer) req.getAttribute("id");

        switch (action) {
            case "add" :
                req.setAttribute("form_title", "New item");
                req.setAttribute("form_action", "/item.do/add");
                req.setAttribute("item", new Item(0, "", new Timestamp(System.currentTimeMillis()), false));
                req.getRequestDispatcher("/templates/item/edit.jsp").forward(req, resp);
                break;
            case "edit" :
                req.setAttribute("form_title", "Edit item");
                req.setAttribute("form_action", "/item.do/edit/" + id);
                req.setAttribute("item", HibStore.instOf().getItem(id));
                req.getRequestDispatcher("/templates/item/edit.jsp").forward(req, resp);
                break;
            case "delete" :
                req.setAttribute("form_title", "Delete item");
                req.setAttribute("form_action", "/item.do/delete/" + id);
                req.setAttribute("item", HibStore.instOf().getItem(id));
                req.getRequestDispatcher("/templates/item/delete.jsp").forward(req, resp);
                break;
            case "get" :
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(JSONObject.toJSONString(Map.of(id, HibStore.instOf().getItem(id))));
                break;
            case "getAll" :
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                Map<Integer, Item> itemsMap = HibStore.instOf().getItems()
                        .stream().collect(Collectors.toMap(Item::getId, i -> i));
                resp.getWriter().write(JSONObject.toJSONString(itemsMap));
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getAttribute("action");
        Integer id = (Integer) req.getAttribute("id");
        req.setCharacterEncoding("UTF-8");
        String descr;
        Timestamp created;
        boolean done;
        List<String> errors;
        switch (action) {
            case "add" :
                descr = req.getParameter("descr");
                created = Timestamp.valueOf(req.getParameter("created"));
                done = (req.getParameter("done") != null && req.getParameter("done").equals("on"));
                HibStore.instOf().addItem(new Item(0, descr, created, done));
                break;
            case "edit" :
                descr = req.getParameter("descr");
                created = Timestamp.valueOf(req.getParameter("created"));
                done = (req.getParameter("done") != null && req.getParameter("done").equals("on"));
                HibStore.instOf().updateItem(id, new Item(id, descr, created, done));
                break;
            case "delete" :
                HibStore.instOf().deleteItem(id);
                break;
            default:
                break;
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
