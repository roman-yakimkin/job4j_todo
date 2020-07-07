package ru.job4j.todo.servlet;

import org.json.simple.JSONObject;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * The Item Servlet class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.06.2020
 * @version 1.0
 */
public class ItemServlet extends HttpServlet {

    private Map<String, BiFunction<HttpServletRequest, HttpServletResponse, Boolean>> getActions = new HashMap<>();
    private Map<String, BiFunction<HttpServletRequest, HttpServletResponse, Boolean>> postActions = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        getActions.put("add", doGetAddItemAction());
        getActions.put("edit", doGetEditItemAction());
        getActions.put("delete", doGetDeleteItemAction());
        getActions.put("get", doGetGetItemAction());
        getActions.put("getAll", doGetGetAllItemsAction());

        postActions.put("add", doPostAddItemAction());
        postActions.put("edit", doPostEditItemAction());
        postActions.put("delete", doPostDeleteItemAction());
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetAddItemAction() {
        return (req, resp) -> {
            try {
                if (!User.userCanAddItem((User) req.getSession().getAttribute("user"))) {
                    resp.sendRedirect(req.getContextPath() + "/user.do/login");
                    return false;
                }
                req.setAttribute("form_title", "New item");
                req.setAttribute("form_action", "/item.do/add");
                req.setAttribute("item", new Item(0, "", new Timestamp(System.currentTimeMillis()), false, null));
                req.getRequestDispatcher("/templates/item/edit.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetEditItemAction() {
        return (req, resp) -> {
            int id = (Integer) req.getAttribute("id");
            Item item = HibStore.instOf().getItem(id);
            User user = (User) req.getSession().getAttribute("user");
            try {
                if (!User.userCanEditItem(user, item)) {
                    resp.sendRedirect(req.getContextPath() + "/user.do/login");
                    return false;
                }
                req.setAttribute("form_title", "Edit item");
                req.setAttribute("form_action", "/item.do/" + id + "/edit");
                req.setAttribute("item", item);
                req.getRequestDispatcher("/templates/item/edit.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetDeleteItemAction() {
        return (req, resp) -> {
            int id = (Integer) req.getAttribute("id");
            Item item = HibStore.instOf().getItem(id);
            User user = (User) req.getSession().getAttribute("user");
            try {
                if (!User.userCanDeleteItem(user, item)) {
                    resp.sendRedirect(req.getContextPath() + "/user.do/login");
                    return false;
                }
                req.setAttribute("form_title", "Delete item");
                req.setAttribute("form_action", "/item.do/" + id + "/delete");
                req.setAttribute("item", item);
                req.getRequestDispatcher("/templates/item/delete.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetGetItemAction() {
        return (req, resp) -> {
            int id = (Integer) req.getAttribute("id");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            try {
                resp.getWriter().write(JSONObject.toJSONString(Map.of(id, HibStore.instOf().getItem(id))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetGetAllItemsAction() {
        return (req, resp) -> {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Map<Integer, Item> itemsMap = HibStore.instOf().getItems()
                    .stream().collect(Collectors.toMap(Item::getId, i -> i));
            try {
                resp.getWriter().write(JSONObject.toJSONString(itemsMap));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doPostAddItemAction() {
        return (req, resp) -> {
            String descr = req.getParameter("descr");
            Date created = Timestamp.valueOf(req.getParameter("created"));
            boolean done = (req.getParameter("done") != null && req.getParameter("done").equals("on"));
            User author = (User) req.getSession().getAttribute("user");
            HibStore.instOf().addItem(new Item(0, descr, created, done, author));
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doPostEditItemAction() {
        return (req, resp) -> {
            int id = (Integer) req.getAttribute("id");
            String descr = req.getParameter("descr");
            Date created = Timestamp.valueOf(req.getParameter("created"));
            boolean done = (req.getParameter("done") != null && req.getParameter("done").equals("on"));
            User author = (User) req.getSession().getAttribute("user");
            HibStore.instOf().updateItem(new Item(id, descr, created, done, author));
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doPostDeleteItemAction() {
        return (req, resp) -> {
            int id = (Integer) req.getAttribute("id");
            HibStore.instOf().deleteItem(id);
            return true;
        };
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("real path = " + req.getServletContext().getRealPath(""));
        String action = (String) req.getAttribute("action");
        getActions.get(action).apply(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getAttribute("action");
        postActions.get(action).apply(req, resp);
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
