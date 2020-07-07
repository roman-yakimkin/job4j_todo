package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Model class for roles
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 03.07.2020
 * @version 1.0
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public Role() { }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Role of(String name) {
        Role role = new Role();
        role.name = name;
        return role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
