package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * The class for model of Item
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.06.2020
 * @version 1.0
 */
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descr;
    private Date created;
    private boolean done;

    public Item() { }

    public Item(int id, String descr, Date created, boolean done) {
        this.id = id;
        this.descr = descr;
        this.created = created;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getCreated() {
        return created;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
       this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return getId() == item.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
