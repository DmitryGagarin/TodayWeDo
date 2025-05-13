package org.example.todaywedo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

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

    public List<TaskEntity> findAll() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<TaskEntity> tasks = em.createQuery(
            "SELECT t FROM TaskEntity t", TaskEntity.class
        ).getResultList();
        tx.commit();
        return tasks;
    }
}
