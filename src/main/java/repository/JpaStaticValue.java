package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaStaticValue {

    private static final EntityManagerFactory FACTORY=
            Persistence.createEntityManagerFactory("warehousePU");

    public JpaStaticValue() {
    }

    public static EntityManager getEntityManager(){
        return FACTORY.createEntityManager();
    }

    public static void close() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}
