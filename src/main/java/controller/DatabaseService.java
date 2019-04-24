/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import model.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.Collection;

/**
 *
 * @author asroth
 */
public class DatabaseService {
    
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("TodoPersistence");
    private EntityManager entityManager = factory.createEntityManager();
    
    public DatabaseService(){
    }
    
    public boolean connect() {
        Task task = new Task();
        task.setName("test");
        task.setDescription("test");
        entityManager.persist(task);
        return entityManager.find(Task.class, (long)1) != null && entityManager.contains(task);
    }

    public boolean persistTask(Task task){
        entityManager.persist(task);
        return entityManager.contains(task);
    }

    public Task retrieveTaskByID(long ID){
        return entityManager.find(Task.class, ID);
    }

    public Collection<Task> getAllTasks(){
        Query query = entityManager.createQuery("SELECT t FROM Task t");
        return (Collection<Task>) query.getResultList();
    }
}
