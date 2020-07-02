package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

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
}
