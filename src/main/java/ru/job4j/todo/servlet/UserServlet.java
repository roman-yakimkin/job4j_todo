package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Role;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The servlet class for User entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 04.07.2020
 * @version 1.0
 */
public class UserServlet extends HttpServlet {
    private Map<String, BiFunction<HttpServletRequest, HttpServletResponse, Boolean>> getActions = new HashMap<>();
    private Map<String, BiFunction<HttpServletRequest, HttpServletResponse, Boolean>> postActions = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        getActions.put("register", doGetRegisterUserAction());
        getActions.put("login", doGetLoginUserAction());
        getActions.put("logout", doGetLogoutUserAction());

        postActions.put("register", doPostRegisterUserAction());
        postActions.put("login", doPostLoginUserAction());
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetRegisterUserAction() {
        return (req, resp) -> {
            req.setAttribute("form_title", "Register");
            req.setAttribute("form_action", "/user.do/register");
            try {
                req.getRequestDispatcher("/templates/user/register.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetLoginUserAction() {
        return (req, resp) -> {
            req.setAttribute("form_title", "Login");
            req.setAttribute("form_action", "/user.do/login");
            try {
                req.getRequestDispatcher("/templates/user/login.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doGetLogoutUserAction() {
        return (req, resp) -> {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", null);
            try {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doPostLoginUserAction() {
        return (req, resp) -> {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User user = HibStore.instOf().getUserByEmailAndPassword(email, password);
            try {
                if (user != null) {
                    HttpSession sc = req.getSession();
                    sc.setAttribute("user", user);
                    resp.sendRedirect(req.getContextPath() + "/index.jsp");
                } else {
                    req.setAttribute("message", "Incorrect email or password");
                    req.getRequestDispatcher("/templates/user/login.jsp").forward(req, resp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        };
    }

    protected BiFunction<HttpServletRequest, HttpServletResponse, Boolean> doPostRegisterUserAction() {
        return (req, resp) -> {
            try {
                req.setCharacterEncoding("UTF-8");
                User userWithTheSameEmail = HibStore.instOf().getUserByEmail(req.getParameter("email"));
                if (userWithTheSameEmail != null) {
                    req.setAttribute("message", "The user with the same email already exists in the database");
                    doGet(req, resp);
                } else {
                    String name = req.getParameter("name");
                    String email = req.getParameter("email");
                    String password = req.getParameter("password");
                    Role role = HibStore.instOf().getRole(2);
                    HibStore.instOf().addUser(new User(0, name, email, password, role));
                    resp.sendRedirect(req.getContextPath() + "/user.do/login");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getAttribute("action");
        getActions.get(action).apply(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (String) req.getAttribute("action");
        postActions.get(action).apply(req, resp);
    }
}
