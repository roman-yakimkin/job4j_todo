package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The implementation of the Store interface based on the Hibernate library
 * @author Roman Yakimkin (r.yakimkin@yandex.ru)
 * @since 29.06.2020
 * @version 1.0
 */
public class HibStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private HibStore() {
    }

    public static final class Lazy {
        private static final Store INST = new HibStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T result = command.apply(session);
            tx.commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void addItem(Item item) {
        this.tx(
            session -> session.save(item)
        );
    }

    @Override
    public void updateItem(Item item) {
        this.tx(
           session -> {
               session.update(item);
               return null;
           }
        );
    }

    @Override
    public void deleteItem(int id) {
        this.tx(
           session -> {
              Item item = new Item();
              item.setId(id);
              session.delete(item);
              return null;
           }
        );
    }

    @Override
    public List<Item> getItems() {
        return this.tx(
           session -> session.createQuery("from ru.job4j.todo.model.Item").list()
        );
    }

    @Override
    public Item getItem(int id) {
        return this.tx(
            session -> {
                Query query = session.createQuery("from ru.job4j.todo.model.Item where id = :id");
                query.setParameter("id", id);
                return (Item) query.uniqueResult();
            }
        );
    }
}
