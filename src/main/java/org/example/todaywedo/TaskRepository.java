package org.example.todaywedo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TaskRepository {

    private final EntityManager em;

    public TaskRepository(EntityManager em) {
        this.em = em;
    }

    public TaskEntity save(TaskEntity taskEntity) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(taskEntity);
        tx.commit();
        return taskEntity;
    }
}
