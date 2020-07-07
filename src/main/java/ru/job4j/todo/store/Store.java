package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.Role;
import ru.job4j.todo.model.User;

import java.util.List;

/**
 * The interface for common store class
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.06.2020
 * @version 1.0
 */
public interface Store {
    void addItem(Item item);
    void updateItem(Item item);
    void deleteItem(int id);
    List<Item> getItems();
    Item getItem(int id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    User getUser(int id);
    User getUserByEmail(String email);
    User getUserByEmailAndPassword(String email, String password);
    List<User> getUsers();
    Role getRole(int id);
    List<Role> getRoles();
}
