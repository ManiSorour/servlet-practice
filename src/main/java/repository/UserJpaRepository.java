package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.product.Product;
import model.role.User;

import java.util.List;
import java.util.Optional;

public class UserJpaRepository implements JpaRepository {


    @Override
    public List<User> findAll() {
        EntityManager em = JpaStaticValue.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        EntityManager em = JpaStaticValue.getEntityManager();
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(User user) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();

        }finally {
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        }finally {
            em.close();
        }

    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User user = em.find(User.class,id);
            if (user != null){
                em.remove(user);
            }
            tx.commit();
        }finally {
            em.close();
        }
    }
}
