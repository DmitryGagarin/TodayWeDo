package org.example.todaywedo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("todayWeDoPU");

    public static EntityManager getEmf() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
