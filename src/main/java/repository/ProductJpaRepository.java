package repository;

import jakarta.persistence.*;
import model.product.Product;

import java.util.List;
import java.util.Optional;

public class ProductJpaRepository implements ProductRepository{
    @Override
    public List<Product> findAll() {
        EntityManager em = JpaStaticValue.getEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p",Product.class);
           return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        EntityManager em = JpaStaticValue.getEntityManager();
        try {
            Product product = em.find(Product.class ,id);
            return Optional.ofNullable(product);
        }finally {
            em.close();
        }
    }

    @Override
    public void save(Product product) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(product);
            tr.commit();
        }finally {
            em.close();
        }
    }

    @Override
    public void update(Product product) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tr = em.getTransaction() ;
        try {
            tr.begin();
            em.merge(product);
            tr.commit();
        }finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaStaticValue.getEntityManager();
        EntityTransaction tr = em.getTransaction();
        try {

            tr.begin();
            Product product = em.find(Product.class,id);
            if (product != null){
                em.remove(product);
            }
            tr.commit();
        }finally {
            em.close();
        }



    }
}
