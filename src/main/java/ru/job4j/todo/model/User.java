package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * The User entity
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 03.07.2020
 * @version 1.0
 */
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() { }

    public User(int id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User of(String name, String email, String password, Role role) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.password = password;
        user.role = role;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static boolean userCanAddItem(User user) {
        return user != null;
    }

    public static boolean userCanEditItem(User user, Item item) {
        return (user != null) && (user.equals(item.getAuthor()) || user.getRole().getName().equals("admin"));
    }

    public static boolean userCanDeleteItem(User user, Item item) {
        return (user != null) && (user.equals(item.getAuthor()) || user.getRole().getName().equals("admin"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
