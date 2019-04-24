/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import model.Task;

import java.sql.ResultSet;
import java.util.Collection;

/**
 *
 * @author asroth
 */
@Path("/task")
public class TaskService {
    DatabaseService databaseService = new DatabaseService();

    //Instead of returning strings with status descriptions, return responses with appropriate response codes.

    @Path("/ping")
    @GET  
    @Produces(MediaType.TEXT_PLAIN)
    public String pingConnection() {
        return "Connection: " + databaseService.connect();
    }

    @Path("/new")
    @POST
    public String newTask(@QueryParam("name") String name, @QueryParam("description") String description){
        Task newTask = new Task();
        newTask.setName(name);
        newTask.setDescription(description);
        return databaseService.persistTask(newTask) ? "Task successfully added" : "Task could not be be added";
    }

    @Path("/find/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTaskByID(@PathParam("id") long id){
        Task task = databaseService.retrieveTaskByID(id);
        return task != null ? task.toString() : "Task couldn't be found";
    }

    @Path("/findall")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllTasks(){
        Collection<Task> taskCollection = databaseService.getAllTasks();
        StringBuilder stringBuilder = new StringBuilder();
        taskCollection.stream().forEach(t -> stringBuilder.append(t.toString()).append("\n"));
        return stringBuilder.toString();
    }
}
